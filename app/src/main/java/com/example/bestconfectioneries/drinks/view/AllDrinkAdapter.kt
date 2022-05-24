package com.example.bestconfectioneries.drinks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.databinding.DrinkRowBinding
import com.example.bestconfectioneries.databinding.MoreAboutDrinkBinding
import com.example.bestconfectioneries.drinks.model.Drink

class AllDrinkAdapter(private val dataSet: ArrayList<Drink>) :
    RecyclerView.Adapter<AllDrinkAdapter.AllDrinkViewHolder>() {

    class AllDrinkViewHolder(private var binding: DrinkRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drink = drink
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllDrinkViewHolder {
        return AllDrinkViewHolder(
            DrinkRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: AllDrinkViewHolder, position: Int) {
        val drinkToBind = dataSet[position]
        viewHolder.bind(drinkToBind)
        viewHolder.itemView.setOnClickListener {
            showDrink(dataSet[position], viewHolder)
        }
    }

    override fun getItemCount() = dataSet.size

    private fun showDrink(drink: Drink, viewHolder: RecyclerView.ViewHolder) {
        val drinkWindow =
            MoreAboutDrinkBinding.inflate(LayoutInflater.from(viewHolder.itemView.context))
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(drinkWindow.root)
        val alertDialog = builder.show()
        drinkWindow.drink = drink
        drinkWindow.closeWindowButton.setOnClickListener {
            alertDialog.dismiss()
        }
        drinkWindow.editDrinkButton.visibility = View.GONE
    }
}