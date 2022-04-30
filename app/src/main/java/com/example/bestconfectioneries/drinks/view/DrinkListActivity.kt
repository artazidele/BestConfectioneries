package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityDrinkListBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.helpers.Network

class DrinkListActivity : AppCompatActivity() {
    private val viewModel: DrinkViewModel by viewModels()
    private lateinit var binding: ActivityDrinkListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Drink List")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.allDrinkRecyclerView.layoutManager = LinearLayoutManager(this)
        if (Network().checkConnection(this) == true) {
            refreshDrinkList()
        }
    }

    private fun refreshDrinkList() {
        viewModel.getAllDrinks {
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = DrinkListAdapter(it!!, this)
                binding.allDrinkRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drink_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> Navigation().fromTo(this, MainActivity())
            R.id.add -> Navigation().fromTo(this, AddDrinkActivity())
            else -> Navigation().fromTo(this, MainActivity())
//            R.id.find_drink -> fromTo(this, MainActivity())
//            R.id.profile -> fromTo(this, AddDrinkActivity())
//            R.id.exit -> fromTo(this, AddDrinkActivity())
        }
        return super.onOptionsItemSelected(item)
    }
}