package com.example.bestconfectioneries.items.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.databinding.ItemRowBinding
import com.example.bestconfectioneries.items.model.Item

class ItemListAdapter(private val dataSet: ArrayList<Item>, private val context: Context, private val confectioneryId: String) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    class ItemListViewHolder(private var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ItemListViewHolder, position: Int) {
        val itemToBind = dataSet[position]
        viewHolder.bind(itemToBind)
        viewHolder.itemView.setOnClickListener {
            showItem(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

    private fun showItem(item: Item) {
        val intent = Intent(context!!, ItemActivity()::class.java)
        intent.putExtra("iId", item.id)
        intent.putExtra("id", confectioneryId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(context!!, intent, null)
    }
}