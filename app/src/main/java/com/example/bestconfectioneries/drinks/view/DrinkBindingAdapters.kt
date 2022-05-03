package com.example.bestconfectioneries.drinks.view

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.drinks.viewmodel.DrinkNetworkStatus

@BindingAdapter("drinkLoadingStatus")
fun bindDrinkLoadingStatus(statusTextView: TextView, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
        }
        DrinkNetworkStatus.ERROR -> {
            statusTextView.visibility = View.GONE
        }
        DrinkNetworkStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("drinkErrorStatus")
fun bindDrinkErrorStatus(statusLinearLayout: LinearLayout, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            statusLinearLayout.visibility = View.GONE
        }
        DrinkNetworkStatus.ERROR -> {
            statusLinearLayout.visibility = View.VISIBLE
        }
        DrinkNetworkStatus.DONE -> {
            statusLinearLayout.visibility = View.GONE
        }
    }
}

@BindingAdapter("drinkStatus")
fun bindDrinkStatus(recyclerView: RecyclerView, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            recyclerView.visibility = View.GONE
        }
        DrinkNetworkStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }
        DrinkNetworkStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
    }
}
