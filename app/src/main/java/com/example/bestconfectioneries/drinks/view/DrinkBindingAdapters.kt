package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import android.util.Log
import android.view.View
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
            Log.d(ContentValues.TAG, "LOAD drinkLoadingStatus")
            statusTextView.visibility = View.VISIBLE
        }
        DrinkNetworkStatus.ERROR -> {
            statusTextView.visibility = View.GONE
        }
        DrinkNetworkStatus.DONE -> {
            Log.d(ContentValues.TAG, "DONE drinkLoadingStatus")
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
            Log.d(ContentValues.TAG, "LOAD drinkLoadingStatus")
            recyclerView.visibility = View.GONE
        }
        DrinkNetworkStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }
        DrinkNetworkStatus.DONE -> {
            Log.d(ContentValues.TAG, "DONE drinkLoadingStatus")
            recyclerView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("oneDrinkStatus")
fun bindOneDrinkStatus(constraintLayout: ConstraintLayout, status: DrinkNetworkStatus?) {
    when (status) {
        DrinkNetworkStatus.LOADING -> {
            Log.d(ContentValues.TAG, "LOAD drinkLoadingStatus")
            constraintLayout.visibility = View.GONE
        }
        DrinkNetworkStatus.ERROR -> {
            constraintLayout.visibility = View.GONE
        }
        DrinkNetworkStatus.DONE -> {
            Log.d(ContentValues.TAG, "DONE drinkLoadingStatus")
            constraintLayout.visibility = View.VISIBLE
        }
    }
}

//@BindingAdapter("oneDrinkLoadingStatus")
//fun bindOneDrinkLoadingStatus(statusTextView: TextView, status: DrinkNetworkStatus?) {
//    when (status) {
//        DrinkNetworkStatus.LOADING -> {
//            Log.d(ContentValues.TAG, "LOAD LOAD LOAD")
//            statusTextView.visibility = View.VISIBLE
//        }
//        DrinkNetworkStatus.ERROR -> {
//            statusTextView.visibility = View.GONE
//        }
//        DrinkNetworkStatus.DONE -> {
//            Log.d(ContentValues.TAG, "DONE LOAD DONE LOAD")
//            statusTextView.visibility = View.GONE
//        }
//    }
//}
//
//@BindingAdapter("oneDrinkErrorStatus")
//fun bindOneDrinkErrorStatus(statusLinearLayout: LinearLayout, status: DrinkNetworkStatus?) {
//    when (status) {
//        DrinkNetworkStatus.LOADING -> {
//            statusLinearLayout.visibility = View.GONE
//        }
//        DrinkNetworkStatus.ERROR -> {
//            statusLinearLayout.visibility = View.VISIBLE
//        }
//        DrinkNetworkStatus.DONE -> {
//            statusLinearLayout.visibility = View.GONE
//        }
//    }
//}

