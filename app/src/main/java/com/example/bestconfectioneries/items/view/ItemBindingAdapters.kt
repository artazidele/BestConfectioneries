package com.example.bestconfectioneries.items.view

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkNetworkStatus
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.viewmodel.ItemNetworkStatus

// Binding Adapters for ItemListActivity, AddItemActivity, EditItemActivity status
@BindingAdapter("itemLoadingStatus")
fun bindItemLoadingStatus(statusTextView: TextView, status: ItemNetworkStatus?) {
    when (status) {
        ItemNetworkStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
        }
        ItemNetworkStatus.ERROR -> {
            statusTextView.visibility = View.GONE
        }
        ItemNetworkStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("itemErrorStatus")
fun bindItemErrorStatus(statusLinearLayout: LinearLayout, status: ItemNetworkStatus?) {
    when (status) {
        ItemNetworkStatus.LOADING -> {
            statusLinearLayout.visibility = View.GONE
        }
        ItemNetworkStatus.ERROR -> {
            statusLinearLayout.visibility = View.VISIBLE
        }
        ItemNetworkStatus.DONE -> {
            statusLinearLayout.visibility = View.GONE
        }
    }
}

@BindingAdapter("itemStatus")
fun bindItemStatus(recyclerView: RecyclerView, status: ItemNetworkStatus?) {
    when (status) {
        ItemNetworkStatus.LOADING -> {
            recyclerView.visibility = View.GONE
        }
        ItemNetworkStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }
        ItemNetworkStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("oneItemStatus")
fun bindOneItemStatus(constraintLayout: ConstraintLayout, status: ItemNetworkStatus?) {
    when (status) {
        ItemNetworkStatus.LOADING -> {
            constraintLayout.visibility = View.GONE
        }
        ItemNetworkStatus.ERROR -> {
            constraintLayout.visibility = View.GONE
        }
        ItemNetworkStatus.DONE -> {
            constraintLayout.visibility = View.VISIBLE
        }
    }
}

// Binding Adapters for ItemRow
@BindingAdapter("rowNameI")
fun bindRowName(textView: TextView, name: String) {
    textView.text = name
}

@BindingAdapter("rowDescriptionI")
fun bindRowDescription(textView: TextView, description: String) {
    textView.text = description
}

@BindingAdapter("rowPriceI")
fun bindRowPrice(textView: TextView, item: Item) {
    if (item.centi < 10) {
        textView.text = item.eiro.toString() + ".0" + item.centi.toString() + "€"
    } else {
        textView.text = item.eiro.toString() + "." + item.centi.toString() + "€"
    }
}


