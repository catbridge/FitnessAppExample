package com.example.fitnessanimeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.fitnessanimeapp.databinding.DaysListItemBinding

class DaysAdapter(private var listener: Listener) : ListAdapter<DayModel,DayViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DayViewHolder(
            binding = DaysListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DayModel>() {
            override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}