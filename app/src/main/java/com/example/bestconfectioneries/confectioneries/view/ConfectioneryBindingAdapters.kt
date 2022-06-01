package com.example.bestconfectioneries.confectioneries.view

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryNetworkStatus

// Binding Adapters for Confectionery status
@BindingAdapter("confectioneryLoadingStatus")
fun bindConfectioneryLoadingStatus(statusTextView: TextView, status: ConfectioneryNetworkStatus?) {
    when (status) {
        ConfectioneryNetworkStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
        }
        ConfectioneryNetworkStatus.ERROR -> {
            statusTextView.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("confectioneryErrorStatus")
fun bindConfectioneryErrorStatus(statusLinearLayout: LinearLayout, status: ConfectioneryNetworkStatus?) {
    when (status) {
        ConfectioneryNetworkStatus.LOADING -> {
            statusLinearLayout.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.ERROR -> {
            statusLinearLayout.visibility = View.VISIBLE
        }
        ConfectioneryNetworkStatus.DONE -> {
            statusLinearLayout.visibility = View.GONE
        }
    }
}

@BindingAdapter("confectioneryStatus")
fun bindConfectioneryStatus(recyclerView: RecyclerView, status: ConfectioneryNetworkStatus?) {
    when (status) {
        ConfectioneryNetworkStatus.LOADING -> {
            recyclerView.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("oneConfectioneryStatus")
fun bindOneConfectioneryStatus(constraintLayout: ConstraintLayout, status: ConfectioneryNetworkStatus?) {
    when (status) {
        ConfectioneryNetworkStatus.LOADING -> {
            constraintLayout.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.ERROR -> {
            constraintLayout.visibility = View.GONE
        }
        ConfectioneryNetworkStatus.DONE -> {
            constraintLayout.visibility = View.VISIBLE
        }
    }
}

// Binding Adapters for ConfectioneryRow
@BindingAdapter("rowNameC")
fun bindRowNameC(textView: TextView, name: String) {
    textView.text = name
}

@BindingAdapter("rowDescriptionC")
fun bindRowDescriptionC(textView: TextView, description: String) {
    textView.text = description
}

@BindingAdapter("rowAddressC")
fun bindRowAddressC(textView: TextView, address: String) {
    textView.text = address
}