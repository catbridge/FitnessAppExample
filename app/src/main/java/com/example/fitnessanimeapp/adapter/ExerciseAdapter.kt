package com.example.fitnessanimeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.fitnessanimeapp.databinding.DaysListItemBinding
import com.example.fitnessanimeapp.databinding.ExerciseListItemBinding

class ExerciseAdapter() : ListAdapter<ExerciseModel,ExerciseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(
            binding = ExerciseListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExerciseModel>() {
            override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}