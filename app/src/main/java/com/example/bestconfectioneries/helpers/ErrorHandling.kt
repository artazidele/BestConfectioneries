package com.example.bestconfectioneries.helpers

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.bestconfectioneries.R

class ErrorHandling {
    public fun showErrorWindow(context: Context, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.error_window, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.error_info_tv).text = message
        dialogView.findViewById<Button>(R.id.okay_btn).setOnClickListener {
            alertDialog.dismiss()
        }
    }
}