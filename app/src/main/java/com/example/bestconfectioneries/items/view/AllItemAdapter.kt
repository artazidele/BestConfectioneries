package com.example.bestconfectioneries.items.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.databinding.ItemRowBinding
import com.example.bestconfectioneries.items.model.Item

class AllItemAdapter(private val dataSet: ArrayList<Item>) :
    RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {

    class AllItemViewHolder(private var binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllItemViewHolder {
        return AllItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: AllItemViewHolder, position: Int) {
        val itemToBind = dataSet[position]
        viewHolder.bind(itemToBind)
        viewHolder.itemView.setOnClickListener {
            showItem(dataSet[position], viewHolder)
        }
    }

    override fun getItemCount() = dataSet.size

    private fun showItem(item: Item, viewHolder: RecyclerView.ViewHolder) {
//        val drinkWindow =
//            MoreAboutDrinkBinding.inflate(LayoutInflater.from(viewHolder.itemView.context))
//        val builder = AlertDialog.Builder(viewHolder.itemView.context)
//            .setView(drinkWindow.root)
//        val alertDialog = builder.show()
//        drinkWindow.drink = drink
//        drinkWindow.closeWindowButton.setOnClickListener {
//            alertDialog.dismiss()
//        }
//        drinkWindow.editDrinkButton.visibility = View.GONE
    }

}