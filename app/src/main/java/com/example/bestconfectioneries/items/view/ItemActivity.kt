package com.example.bestconfectioneries.items.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.bestconfectioneries.databinding.ActivityItemBinding
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel

class ItemActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityItemBinding
    private lateinit var itemId: String
    private lateinit var item: Item
    private lateinit var nameTV: TextView
    private lateinit var itemImageView: ImageView
    private lateinit var imageURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Confectionery's Item")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        itemId = intent.getStringExtra("id").toString()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        nameTV = binding.itemName
        itemImageView = binding.itemPhoto
        getItem()
        binding.tryBtn.setOnClickListener {
            getItem()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> Navigation().fromTo(this, ItemListActivity())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getItem() {
        viewModel.getImage(itemId) {
            if (it?.length != 0) {
                imageURL = it!!
                viewModel.getOneItem(itemId) {
                    if (it?.id != null) {
                        item = it
                        updateUI()
                    }
                }
            }
        }

    }

    private fun updateUI() {
        nameTV.setText(item.name)
        Glide.with(this@ItemActivity)
            .load(imageURL)
            .into(itemImageView)
    }
}
