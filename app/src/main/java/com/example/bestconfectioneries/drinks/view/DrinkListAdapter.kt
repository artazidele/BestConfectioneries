package com.example.bestconfectioneries.drinks.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.drinks.model.Drink

class DrinkListAdapter(private val dataSet: ArrayList<Drink>, private val context: Context) :
    RecyclerView.Adapter<DrinkListAdapter.DrinkListViewHolder>() {

    class DrinkListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textViewD: TextView
        init {
            textView = view.findViewById(R.id.drink_title)
            textViewD = view.findViewById(R.id.drink_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DrinkListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.drink_row, viewGroup, false)

        return DrinkListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: DrinkListViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name
        viewHolder.textViewD.text = dataSet[position].description
        viewHolder.itemView.setOnClickListener {
            showDrink(dataSet[position], viewHolder)
        }
    }
    override fun getItemCount() = dataSet.size

    private fun editDrink(drink: Drink) {
        val intent = Intent(context!!, EditDrinkActivity()::class.java)
        intent.putExtra("id", drink.id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(context!!, intent, null)
    }

    private fun showDrink(drink: Drink, viewHolder: RecyclerView.ViewHolder) {
        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.more_about_drink, null)
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<Button>(R.id.close_window_button).setOnClickListener {
            alertDialog.dismiss()
        }
        val userConfectioneryId = "1" // get current user confectioneryId
        val confectioneryId = "1" // get current confectioneryId
        if (userConfectioneryId != confectioneryId) {
            dialogView.findViewById<Button>(R.id.edit_drink_button).visibility = View.GONE
        }
        dialogView.findViewById<Button>(R.id.edit_drink_button).setOnClickListener {
            alertDialog.dismiss()
            editDrink(drink)
        }
    }
}