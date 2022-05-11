package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkNetworkStatus

// Binding Adapters for DrinkListActivity, AddDrinkActivity, EditDrinkActivity status
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

// Binding Adapters for DrinkRow
@BindingAdapter("rowName")
fun bindRowName(textView: TextView, name: String) {
    textView.text = name
}

@BindingAdapter("rowDescription")
fun bindRowDescription(textView: TextView, description: String) {
    textView.text = description
}

@BindingAdapter("rowPrice")
fun bindRowPrice(textView: TextView, drink: Drink) {
    if (drink.centi < 10) {
        textView.text = drink.eiro.toString() + ".0" + drink.centi.toString() + "€"
    } else {
        textView.text = drink.eiro.toString() + "." + drink.centi.toString() + "€"
    }
}

// Binding Adapters for MoreAboutDrink
@BindingAdapter("drinkType")
fun bindMoreType(textView: TextView, drink: Drink) {
    if (drink.coffee == true) {
        textView.text = "Coffee"
    } else if (drink.tea == true) {
        textView.text = "Tea"
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("moreName")
fun bindMoreName(textView: TextView, name: String) {
    textView.text = name
}

@BindingAdapter("moreDescription")
fun bindMoreDescription(textView: TextView, description: String) {
    textView.text = description
}

@BindingAdapter("moreCapacity")
fun bindMoreCapacity(textView: TextView, capacity: Int) {
    textView.text = "Capacity: " + capacity.toString() + "ml"
}

@BindingAdapter("morePrice")
fun bindMorePrice(textView: TextView, drink: Drink) {
    if (drink.centi < 10) {
        textView.text = "Price: " + drink.eiro.toString() + ".0" + drink.centi.toString() + "€"
    } else {
        textView.text = "Price: " + drink.eiro.toString() + "." + drink.centi.toString() + "€"
    }
}
