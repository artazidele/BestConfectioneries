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
//        val deleteButton: Button
//        val editButton: Button
//
        init {
            textView = view.findViewById(R.id.drink_title)
            textViewD = view.findViewById(R.id.drink_description)
//            deleteButton = view.findViewById(R.id.delete_allergen_button)
//            editButton = view.findViewById(R.id.edit_allergen_button)
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
//        viewHolder.deleteButton.setOnClickListener {
//            Log.d(ContentValues.TAG, "Delete button pressed")
//            deleteAllergen(dataSet[position], viewHolder, position)
//        }
//        viewHolder.editButton.setOnClickListener {
//            editAllergen(dataSet[position], viewHolder, position)
//        }
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
        dialogView.findViewById<Button>(R.id.edit_drink_button).setOnClickListener {
            alertDialog.dismiss()
            editDrink(drink)
//            DrinkListActivity().editDrink(drink)
        }
//        dialogView.findViewById<Button>(R.id.not_delete_button).text = "Tomēr saglabāt alergēnu"
//        dialogView.findViewById<Button>(R.id.not_delete_button).setOnClickListener {
//            alertDialog.dismiss()
//        }
//        dialogView.findViewById<Button>(R.id.delete_button).text = "Dzēst alergēnu"
//        dialogView.findViewById<Button>(R.id.delete_button).setOnClickListener {
//            if (NetworkDataViewModel().checkConnection(viewHolder.itemView.context) == true) {
//                AllergenDataViewModel().getAllergenItems(dataSet[position].id) { itemList ->
//                    if (itemList?.isEmpty() == true) {
//                        Log.d(ContentValues.TAG, "CAN DELETE")
//                        AllergenDataViewModel().deleteOneAllergen(dataSet[position].id) { isDeleted ->
//                            if (isDeleted == true) {
//                                Log.d(ContentValues.TAG, "TRUE")
//                                Log.d(ContentValues.TAG, "Position: " + position.toString())
//                                dataSet.removeAt(position)
//                                notifyDataSetChanged()
//                                alertDialog.dismiss()
//                                Log.d(ContentValues.TAG, "Count: " + itemCount.toString())
//                            } else {
//                                Log.d(ContentValues.TAG, "FALSE")
//                            }
//                        }
//                    } else if (itemList?.isNotEmpty() == true) {
//                        Log.d(ContentValues.TAG, "CANNOT DELETE")
//                    } else {
//                        Log.d(ContentValues.TAG, "NULL")
//                    }
//                }
//            }
//        }
    }
}