package com.example.fitnessanimeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.adapter.ExerciseAdapter
import com.example.fitnessanimeapp.databinding.FragmentExerciseListBinding
import com.example.fitnessanimeapp.utils.FragmentManager
import com.example.fitnessanimeapp.viewModel.MainViewModel

class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var _actionBar: ActionBar? = null
    private val actionBar get() = requireNotNull(_actionBar)

    private val viewModel: MainViewModel by activityViewModels()
    private val adapter = ExerciseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentExerciseListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar.title = getString(R.string.exerciseList)

        with(binding) {
            recyclerViewEx.adapter = adapter
            viewModel.mutableExerciseList.observe(viewLifecycleOwner) {
                for (i in 0 until viewModel.getExerciseCount()){
                    it[i] = it[i].copy(isDone = true)
                }
                adapter.submitList(it)
            }
            button.setOnClickListener {
                FragmentManager.setFragment(
                    WaitingFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}