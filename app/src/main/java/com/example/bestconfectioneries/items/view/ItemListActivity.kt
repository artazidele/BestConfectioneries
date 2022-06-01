package com.example.bestconfectioneries.items.view

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
import com.example.bestconfectioneries.confectioneries.view.OneConfectioneryActivity
import com.example.bestconfectioneries.databinding.ActivityItemListBinding
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel

class ItemListActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityItemListBinding
    private lateinit var confectioneryId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Item List")
        confectioneryId = intent.getStringExtra("id").toString()
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
        viewModel.getThisConfectioneryItems(confectioneryId) {
            if (it?.isNotEmpty() == true) {
                binding.allDrinkRecyclerView.adapter = ItemListAdapter(it!!, this, confectioneryId)
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
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
            R.id.add -> Navigation().fromToStringId(this, AddItemActivity(), confectioneryId)
            else -> Navigation().fromToStringId(this, OneConfectioneryActivity(), confectioneryId)
        }
        return super.onOptionsItemSelected(item)
    }
}