package com.example.bestconfectioneries.drinks.view

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

@BindingAdapter("addDrinkStatus")
fun bindAddDrinkStatus(constraintLayout: ConstraintLayout, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            constraintLayout.visibility = View.VISIBLE
        }
        else -> {
            constraintLayout.visibility = View.INVISIBLE
        }
//        DrinkNetworkStatus.ERROR -> {
//            constraintLayout.visibility = View.GONE
//        }
//        DrinkNetworkStatus.DONE -> {
//            constraintLayout.visibility = View.GONE
//        }
    }
}

@BindingAdapter("addDrinkLayout")
fun bindAddDrinkLayout(constraintLayout: ConstraintLayout, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            constraintLayout.visibility = View.INVISIBLE
        }
        else -> {
            constraintLayout.visibility = View.VISIBLE
        }

//        DrinkNetworkStatus.ERROR -> {
//            constraintLayout.visibility = View.GONE
//        }
//        DrinkNetworkStatus.DONE -> {
//            constraintLayout.visibility = View.GONE
//        }
    }
}
