package com.example.bestconfectioneries.confectioneries.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bestconfectioneries.confectioneries.model.Confectionery
import com.example.bestconfectioneries.databinding.ConfectioneryRowBinding
import com.example.bestconfectioneries.helpers.Navigation

class ConfectioneryListAdapter(private val dataSet: ArrayList<Confectionery>, private val context: Context) :
    RecyclerView.Adapter<ConfectioneryListAdapter.ConfectioneryListViewHolder>() {

    class ConfectioneryListViewHolder(private var binding: ConfectioneryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(confectionery: Confectionery) {
            binding.confectionery = confectionery
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ConfectioneryListViewHolder {
        return ConfectioneryListViewHolder(
            ConfectioneryRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ConfectioneryListViewHolder, position: Int) {
        val confectioneryToBind = dataSet[position]
        viewHolder.bind(confectioneryToBind)
        viewHolder.itemView.setOnClickListener {
            Navigation().fromToStringId(context, OneConfectioneryActivity(), dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size
}