package com.example.fitnessanimeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fitnessanimeapp.fragments.*
import com.example.fitnessanimeapp.utils.FragmentManager
import com.example.fitnessanimeapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(DayFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        when (FragmentManager.currentFragment) {
            is DayFragment ->  super.onBackPressed()
            is ExerciseListFragment -> FragmentManager.setFragment(DayFragment.newInstance(), this)
            is WaitingFragment -> FragmentManager.setFragment(ExerciseListFragment.newInstance(), this)
            is ExerciseFragment -> FragmentManager.setFragment(DayFragment.newInstance(), this)
            //is ExerciseFragment -> FragmentManager.setFragment(ExerciseListFragment.newInstance(), this)
            //is DayFinishFragment -> FragmentManager.setFragment(ExerciseListFragment.newInstance(), this)
        }
    }
}