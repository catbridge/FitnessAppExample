package com.example.fitnessanimeapp.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitnessanimeapp.R

object DialogManager {
    fun showDialog(context: Context, messageId: Int, listener: Listener){
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.attention)
        builder.setMessage(messageId)
        builder.setPositiveButton(R.string.pos_btn){_,_ ->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton(R.string.neg_btn){_,_ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

//    AlertDialog.Builder(requireContext())
//    .setTitle(R.string.info)
//    .setMessage(R.string.text_info)
//    .setPositiveButton(android.R.string.ok, null)
//    .show()

    interface Listener{
        fun onClick()
    }
}