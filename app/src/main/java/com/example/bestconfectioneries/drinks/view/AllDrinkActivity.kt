package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityAllDrinkBinding
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation

class AllDrinkActivity : AppCompatActivity() {
    private val viewModel: DrinkViewModel by viewModels()
    private lateinit var binding: ActivityAllDrinkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("All Drink List")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.allDrinkRecyclerView.layoutManager = LinearLayoutManager(this)
        refreshDrinkList()
        binding.tryBtn.setOnClickListener {
            refreshDrinkList()
        }
    }

    private fun refreshDrinkList() {
        viewModel.getAllDrinks {
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = AllDrinkAdapter(it!!)
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
