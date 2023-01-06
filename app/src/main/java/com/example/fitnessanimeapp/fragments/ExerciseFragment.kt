package com.example.fitnessanimeapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.adapter.ExerciseModel
import com.example.fitnessanimeapp.databinding.FragmentExerciseBinding
import com.example.fitnessanimeapp.utils.FragmentManager
import com.example.fitnessanimeapp.utils.TimeUtils
import com.example.fitnessanimeapp.viewModel.MainViewModel
import pl.droidsonroids.gif.GifDrawable

class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var exerciseCounter = 0
    private var currentDay = 0

    private var timer: CountDownTimer? = null

    private var _actionBar: ActionBar? = null
    private val actionBar get() = requireNotNull(_actionBar)

    private var _exList: ArrayList<ExerciseModel>? = null
    private val exList get() = requireNotNull(_exList)

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentExerciseBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = viewModel.currentDay
        exerciseCounter = viewModel.getExerciseCount()
        _actionBar = (activity as AppCompatActivity).supportActionBar
        with(binding) {
            viewModel.mutableExerciseList.observe(viewLifecycleOwner) {
                _exList = it
                nextExercise()

                buttonNext.setOnClickListener {
                    nextExercise()
                }
            }
        }
    }

    private fun nextExercise() {
        if (exerciseCounter < exList.size) {
            val exercise = exList[exerciseCounter++]
            showExercise(exercise)
            setExerciseType(exercise)
            showNextExercise()
        } else {
            exerciseCounter++
            FragmentManager.setFragment(
                DayFinishFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }

    private fun showExercise(exercise: ExerciseModel) {
        with(binding) {
            if (exercise.image == "plank.jpg") {
                imMain.setImageResource(R.drawable.plank)
            } else {
                imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            }
            tvmName.text = exercise.name

            val title = "$exerciseCounter / ${exList.size}"
            actionBar.title = title
        }
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        with(binding) {
            if (exercise.quantity.startsWith("x")) {
                timer?.cancel()
                progressBar.progress = 0
                tvmTime.text = exercise.quantity
            } else {
                startTimer(exercise)
            }
        }
    }

    private fun showNextExercise() {
        with(binding) {
            if (exerciseCounter < exList.size) {
                val exercise = exList[exerciseCounter]
                if (exercise.image == "plank.jpg") {
                    tvNextImg.setImageResource(R.drawable.plank)
                } else {
                    tvNextImg.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
                }
                setTimeType(exercise)
            } else {
                tvNextImg.setImageDrawable(GifDrawable(root.context.assets, "good_job.gif"))
                tvNextName.text = getString(R.string.good_job)
            }
        }
    }

    private fun setTimeType(exercise: ExerciseModel) {
        with(binding) {
            if (exercise.quantity.startsWith("x")) {
                val nextNameX = exercise.name + ": ${exercise.quantity}"
                tvNextName.text = nextNameX
            } else {
                val nextName = exercise.name +
                        ": ${TimeUtils.getTime(exercise.quantity.toLong() * 1000)}"
                tvNextName.text = nextName
            }
        }
    }

    private fun startTimer(exercise: ExerciseModel) {
        with(binding) {
            progressBar.max = exercise.quantity.toInt() * 1000
            timer?.cancel()
            timer = object : CountDownTimer((exercise.quantity.toLong()) * 1000, 1) {

                override fun onTick(restTime: Long) {
                    tvmTime.text = TimeUtils.getTime(restTime)
                    progressBar.progress = restTime.toInt()

                }

                override fun onFinish() {
                    nextExercise()
                }
            }
                .start()

        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.savePref(currentDay.toString(), exerciseCounter - 1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}