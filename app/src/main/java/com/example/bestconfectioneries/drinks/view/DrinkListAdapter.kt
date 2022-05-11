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
import com.example.bestconfectioneries.databinding.DrinkRowBinding
import com.example.bestconfectioneries.databinding.MoreAboutDrinkBinding
import com.example.bestconfectioneries.drinks.model.Drink

class DrinkListAdapter(private val dataSet: ArrayList<Drink>, private val context: Context) :
    RecyclerView.Adapter<DrinkListAdapter.DrinkListViewHolder>() {

    //    class DrinkListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView
//        val textViewD: TextView
//        init {
//            textView = view.findViewById(R.id.drink_title)
//            textViewD = view.findViewById(R.id.drink_description)
//        }
//    }
    class DrinkListViewHolder(private var binding: DrinkRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drink = drink
            binding.executePendingBindings()
        }
    }

//    companion object DiffCallback : DiffUtil.ItemCallback<CatPhoto>() {
//        override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
//            return oldItem.url == newItem.url
//        }
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DrinkListViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.drink_row, viewGroup, false)
//        return DrinkListViewHolder(view)
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

//    override fun onBindViewHolder(viewHolder: DrinkListViewHolder, position: Int) {
//        viewHolder.textView.text = dataSet[position].name
//        viewHolder.textViewD.text = dataSet[position].description
//        viewHolder.itemView.setOnClickListener {
//            showDrink(dataSet[position], viewHolder)
//        }
//    }

    override fun getItemCount() = dataSet.size

    private fun editDrink(drink: Drink) {
        val intent = Intent(context!!, EditDrinkActivity()::class.java)
        intent.putExtra("id", drink.id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(context!!, intent, null)
    }

    private fun showDrink(drink: Drink, viewHolder: RecyclerView.ViewHolder) {
        val drinkWindow = MoreAboutDrinkBinding.inflate(LayoutInflater.from(viewHolder.itemView.context))
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(drinkWindow.root)
        val alertDialog = builder.show()
        drinkWindow.drink = drink
//        val dialogView = LayoutInflater.from(viewHolder.itemView.context)
//            .inflate(R.layout.more_about_drink, null)
//        val builder = AlertDialog.Builder(viewHolder.itemView.context)
//            .setView(dialogView)
//        val alertDialog = builder.show()
//        if (drink.tea == true) {
//            dialogView.findViewById<TextView>(R.id.tea_or_coffee_tv).text = "Tea"
//        } else if (drink.coffee == true) {
//            dialogView.findViewById<TextView>(R.id.tea_or_coffee_tv).text = "Coffee"
//        } else {
//            dialogView.findViewById<TextView>(R.id.tea_or_coffee_tv).visibility = View.INVISIBLE
//        }
//        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = drink.name
//        dialogView.findViewById<TextView>(R.id.drink_description_tv).text = drink.description
//        dialogView.findViewById<TextView>(R.id.drink_capacity_tv).text =
//            "Capacity: " + drink.capacity.toString() + "ml"
//        if (drink.centi < 10) {
//            dialogView.findViewById<TextView>(R.id.drink_price_tv).text =
//                "Price: " + drink.eiro.toString() + ".0" + drink.centi.toString() + " €"
//        } else {
//            dialogView.findViewById<TextView>(R.id.drink_price_tv).text =
//                "Price: " + drink.eiro.toString() + "." + drink.centi.toString() + " €"
//        }
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
