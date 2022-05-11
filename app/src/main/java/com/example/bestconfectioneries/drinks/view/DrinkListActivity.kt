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
import com.example.bestconfectioneries.databinding.ActivityDrinkListBinding
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation

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
        refreshDrinkList()
        binding.tryBtn.setOnClickListener {
            refreshDrinkList()
        }
    }

    private fun refreshDrinkList() {
        viewModel.getThisConfectioneryDrinks("1") {
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = DrinkListAdapter(it!!, this)
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drink_menu, menu)
        menu?.findItem(R.id.add)?.setVisible(false)
        val userConfectioneryId = "1" // get current user confectioneryId
        val confectioneryId = "1" // get current confectioneryId
        // There will be for cycle because there will be 1 or more confectioneriesId for 1 confectioner
        if (userConfectioneryId == confectioneryId) {
            menu?.findItem(R.id.add)?.setVisible(true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> Navigation().fromTo(this, AddDrinkActivity())
            else -> Navigation().fromTo(this, MainActivity())
        }
        return super.onOptionsItemSelected(item)
    }
}
