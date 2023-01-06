package com.example.fitnessanimeapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessanimeapp.R

object FragmentManager {
    var currentFragment : Fragment? = null

    fun setFragment(newFragment: Fragment, activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.placeholder, newFragment)
            .commit()
        currentFragment = newFragment
    }
}