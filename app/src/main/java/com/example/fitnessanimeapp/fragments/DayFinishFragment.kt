package com.example.fitnessanimeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.databinding.FragmentDayFinishBinding
import com.example.fitnessanimeapp.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable

class DayFinishFragment : Fragment() {

    private var _binding: FragmentDayFinishBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var _actionBar: ActionBar? = null
    private val actionBar get() = requireNotNull(_actionBar)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDayFinishBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar.title = getString(R.string.well_done)
        startTimer()
    }

    private fun startTimer() {
        with(binding) {
            imgFinish.setImageDrawable(
                GifDrawable(
                    root.context.assets, "clapping-anime.gif"
                )
            )

            btnFinish.setOnClickListener {
                FragmentManager.setFragment(
                    DayFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()

    }
}