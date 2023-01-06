package com.example.fitnessanimeapp.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.adapter.DayModel
import com.example.fitnessanimeapp.adapter.DaysAdapter
import com.example.fitnessanimeapp.adapter.ExerciseModel
import com.example.fitnessanimeapp.adapter.Listener
import com.example.fitnessanimeapp.databinding.FragmentDaysBinding
import com.example.fitnessanimeapp.utils.DialogManager
import com.example.fitnessanimeapp.utils.FragmentManager
import com.example.fitnessanimeapp.viewModel.MainViewModel

class DayFragment : Fragment(), Listener {

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var _actionBar: ActionBar? = null
    private val actionBar get() = requireNotNull(_actionBar)

    private val viewModel: MainViewModel by activityViewModels()

    private val adapter = DaysAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDaysBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentDay = 0
        _actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar.title = getString(R.string.dayList)
        with(binding) {
            recyclerView.adapter = adapter
            adapter.submitList(fillDaysArray())

            buttonClear.isVisible = binding.progressBar.progress != 0

            buttonClear.setOnClickListener {
                DialogManager.showDialog(
                    activity as AppCompatActivity,
                    R.string.reset_all_message,
                    object : DialogManager.Listener {
                        override fun onClick() {
                            viewModel.pref?.edit()?.clear()?.apply()
                            adapter.submitList(fillDaysArray())
                        }
                    }
                )
            }
        }
    }


    private fun fillDaysArray(): ArrayList<DayModel> {
        val tempArray = ArrayList<DayModel>()
        var completedDaysCounter = 0
        resources.getStringArray(R.array.days).forEach {
            viewModel.currentDay++
            val exListSize = it.split(",").size
            tempArray.add(DayModel(it, 0, viewModel.getExerciseCount() == exListSize))
        }
        binding.progressBar.max = tempArray.size
        tempArray.forEach {
            if (it.isDone) completedDaysCounter++
        }
        updateDaysProgress(tempArray.size - completedDaysCounter, tempArray.size)
        return tempArray
    }

    private fun updateDaysProgress(restDays: Int, days: Int) {
        with(binding) {
            val rDays = "$restDays " + getString(R.string.rest_days)
            daysLeft.text = rDays
            progressBar.progress = days - restDays
        }

    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercises)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], exerciseArray[2], false))
        }
        viewModel.mutableExerciseList.value = tempList
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {
            fillExerciseList(day)
            viewModel.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity
            )
        }else{
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        viewModel.savePref(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        viewModel.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }
                }
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DayFragment()
    }
}