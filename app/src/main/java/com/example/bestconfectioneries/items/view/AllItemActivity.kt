package com.example.bestconfectioneries.items.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityAllDrinkBinding
import com.example.bestconfectioneries.databinding.ActivityAllItemBinding
import com.example.bestconfectioneries.drinks.view.AllDrinkAdapter
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel

class AllItemActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityAllItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("All Item List")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.allDrinkRecyclerView.layoutManager = LinearLayoutManager(this)
        refreshItemList()
        binding.tryBtn.setOnClickListener {
            refreshItemList()
        }
    }

    private fun refreshItemList() {
        viewModel.getAllItems {
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = AllItemAdapter(it!!)
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> Navigation().fromTo(this, MainActivity())
        }
        return super.onOptionsItemSelected(item)
    }
}