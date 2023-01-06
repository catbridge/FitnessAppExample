package com.example.fitnessanimeapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object TimeUtils {
     private val prepFormatter = SimpleDateFormat("ss")
     fun getPrepareTime(time: Long): String{
          val cv = Calendar.getInstance()
          cv.timeInMillis = time
          return prepFormatter.format(cv.time)
     }
     private val mainFormatter = SimpleDateFormat("mm:ss")
     fun getTime(time: Long): String{
          val cv = Calendar.getInstance()
          cv.timeInMillis = time
          return mainFormatter.format(cv.time)
     }
}