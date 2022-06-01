package com.example.bestconfectioneries.helpers

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.bestconfectioneries.drinks.view.AddDrinkActivity

class Navigation {
    public fun fromTo(fromContext: Context, toContext: Context) {
        val intent = Intent(fromContext!!, toContext::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(fromContext!!, intent, null)
    }

    public fun fromToStringId(fromContext: Context, toContext: Context, id: String) {
        Log.d("TO", toContext.toString())
        val intent = Intent(fromContext!!, toContext::class.java)
        intent.putExtra("id", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(fromContext!!, intent, null)
    }
}