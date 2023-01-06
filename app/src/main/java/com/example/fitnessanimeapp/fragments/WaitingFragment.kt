package com.example.fitnessanimeapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.databinding.WaitingFragmentBinding
import com.example.fitnessanimeapp.utils.FragmentManager
import com.example.fitnessanimeapp.utils.TimeUtils
import kotlin.properties.Delegates

class WaitingFragment : Fragment() {

    private var _binding: WaitingFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var _actionBar: ActionBar? = null
    private val actionBar get() = requireNotNull(_actionBar)

    private var timer: CountDownTimer by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return WaitingFragmentBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar.title = getString(R.string.prepare)

        startTimer()
    }

    private fun startTimer() {
        with(binding) {
            tvWaitngPB.max = COUNT_DOWN_TIME.toInt()
            timer = object : CountDownTimer(COUNT_DOWN_TIME, 1) {
                override fun onTick(restTime: Long) {
                    tvTimer.text = TimeUtils.getPrepareTime(restTime)
                    tvWaitngPB.progress = restTime.toInt()
                }

                override fun onFinish() {
                    FragmentManager.setFragment(
                        ExerciseFragment.newInstance(),
                        activity as AppCompatActivity
                    )
                }
            }
                .start()

        }
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
        private const val COUNT_DOWN_TIME = 6000L
    }
}