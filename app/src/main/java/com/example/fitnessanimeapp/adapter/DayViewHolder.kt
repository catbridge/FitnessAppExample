package com.example.fitnessanimeapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.databinding.DaysListItemBinding

class DayViewHolder(
    private val binding: DaysListItemBinding
): RecyclerView.ViewHolder(binding.root){

    fun setData(day: DayModel, listener: Listener) {
        with(binding) {
            val dayPosition = root.context.getString(R.string.day) + " ${adapterPosition + 1}"
            dayNumber.text = dayPosition
            val daysArray = day.exercises.split(",")
            var exCounter = 0
            for (i in daysArray) {
                if (i != "0" && i != "1")
                    exCounter++
            }
            val exercises = root.context.getString(R.string.exercises) + exCounter.toString()
            exAmount.text = exercises
            checkBox.isChecked = day.isDone
            itemView.setOnClickListener {
                listener.onClick(day.copy(dayNumber = adapterPosition + 1))
            }
        }
    }

}