package com.example.fitnessanimeapp.adapter

import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessanimeapp.R
import com.example.fitnessanimeapp.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseViewHolder(
    private val binding: ExerciseListItemBinding
): RecyclerView.ViewHolder(binding.root){

    fun setData(exercise: ExerciseModel){
        with(binding){
            tvName.text = exercise.name
            tvCount.text = exercise.quantity
            checkBoxIn.isChecked = exercise.isDone
            if(exercise.image == "plank.jpg"){
                imEx.setImageResource(R.drawable.plank)
            }else{
                imEx.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
            }
        }
    }

}