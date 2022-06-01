package com.example.bestconfectioneries.drinks.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.databinding.DrinkRowBinding
import com.example.bestconfectioneries.databinding.MoreAboutDrinkBinding
import com.example.bestconfectioneries.drinks.model.Drink

class DrinkListAdapter(private val dataSet: ArrayList<Drink>, private val context: Context, private val confectioneryId: String) :
    RecyclerView.Adapter<DrinkListAdapter.DrinkListViewHolder>() {

    class DrinkListViewHolder(private var binding: DrinkRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drink = drink
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DrinkListViewHolder {
        return DrinkListViewHolder(
            DrinkRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: DrinkListViewHolder, position: Int) {
        val drinkToBind = dataSet[position]
        viewHolder.bind(drinkToBind)
        viewHolder.itemView.setOnClickListener {
            showDrink(dataSet[position], viewHolder)
        }
    }

    override fun getItemCount() = dataSet.size

    private fun editDrink(drink: Drink) {
        val intent = Intent(context!!, EditDrinkActivity()::class.java)
        intent.putExtra("dId", drink.id)
        intent.putExtra("id", confectioneryId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(context!!, intent, null)
    }

    private fun showDrink(drink: Drink, viewHolder: RecyclerView.ViewHolder) {
        val drinkWindow = MoreAboutDrinkBinding.inflate(LayoutInflater.from(viewHolder.itemView.context))
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(drinkWindow.root)
        val alertDialog = builder.show()
        drinkWindow.drink = drink
        drinkWindow.closeWindowButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val userConfectioneryId = "1" // get current user confectioneriesId
        val confectioneryId = "1" // get current confectioneryId
        // There will be for cycle because there will be 1 or more confectioneriesId for 1 confectioner
        if (userConfectioneryId != confectioneryId) {
            drinkWindow.editDrinkButton.visibility = View.GONE
        }
        drinkWindow.editDrinkButton.setOnClickListener {
            alertDialog.dismiss()
            editDrink(drink)
        }
    }
}
