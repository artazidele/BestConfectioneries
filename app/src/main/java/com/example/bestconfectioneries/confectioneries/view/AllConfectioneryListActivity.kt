package com.example.bestconfectioneries.confectioneries.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryViewModel
import com.example.bestconfectioneries.databinding.ActivityAllConfectioneryListBinding
import com.example.bestconfectioneries.databinding.ActivityDrinkListBinding
import com.example.bestconfectioneries.drinks.view.AddDrinkActivity
import com.example.bestconfectioneries.drinks.view.DrinkListAdapter
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation

class AllConfectioneryListActivity : AppCompatActivity() {
//    private lateinit var getAddressBtn: Button
//    private lateinit var getLocationBtn: Button
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_all_confectionery_list)
//        getAddressBtn = findViewById(R.id.set_location)
//        getLocationBtn = findViewById(R.id.get_location)
//        getLocationBtn.setOnClickListener {
//            ConfectioneryViewModel().getLocation(this, "56.95", "24.1")
//        }
//        getAddressBtn.setOnClickListener {
//            ConfectioneryViewModel().getAddress(this, "Raina Bulvaris 10, Riga")
//        }
//    }

    private val viewModel: ConfectioneryViewModel by viewModels()
    private lateinit var binding: ActivityAllConfectioneryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllConfectioneryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Confectioneries")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.allDrinkRecyclerView.layoutManager = LinearLayoutManager(this)
        refreshConfectioneryList()
        binding.tryBtn.setOnClickListener {
            refreshConfectioneryList()
        }
    }

    private fun refreshConfectioneryList() {
        viewModel.getAllConfectioneries{
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = ConfectioneryListAdapter(it!!, this)
//                Log.d(ContentValues.TAG, "NOT EMPTY")
//            } else {
//                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confectionery_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> Navigation().fromTo(this, AddConfectioneryActivity())
            else -> Navigation().fromTo(this, MainActivity())
        }
        return super.onOptionsItemSelected(item)
    }
}