package com.example.bestconfectioneries.items.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bestconfectioneries.databinding.ActivityItemBinding
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel

class ItemActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityItemBinding
    private lateinit var itemId: String
    private lateinit var confectioneryId: String
    private lateinit var item: Item
    private lateinit var typeTV: TextView
    private lateinit var nameTV: TextView
    private lateinit var itemImageView: ImageView
    private lateinit var descriptionTV: TextView
    private lateinit var allergensTV: TextView
    private lateinit var vegetariansTV: TextView
    private lateinit var vegansTV: TextView
    private lateinit var weightTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var imageURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Confectionery's Item")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        itemId = intent.getStringExtra("iId").toString()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        confectioneryId = intent.getStringExtra("id").toString()
        typeTV = binding.itemType
        nameTV = binding.itemName
        itemImageView = binding.itemPhoto
        descriptionTV = binding.itemDescription
        allergensTV = binding.itemAllergens
        vegetariansTV = binding.itemVegetarians
        vegansTV = binding.itemVegans
        weightTV = binding.itemWeight
        priceTV = binding.itemPrice
        getItem()
        binding.editItemBtn.setOnClickListener {
            editItem()
        }
        binding.tryBtn.setOnClickListener {
            getItem()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> Navigation().fromToStringId(this, ItemListActivity(), confectioneryId)
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
        if (item.bun == true) {
            typeTV.setText("Bun")
        } else if (item.cake == true) {
            typeTV.setText("Cake")
        } else {
            typeTV.setText("Cookies")
        }
        descriptionTV.setText(item.description)
        allergensTV.setText(item.description)
        if (item.forVegans == false) {
            vegansTV.visibility = View.GONE
        }
        if (item.forVegetarians == false) {
            vegetariansTV.visibility = View.GONE
        }
        weightTV.setText(item.weight.toString() + " g")
        if (item.centi < 0) {
            priceTV.setText(item.eiro.toString() + ".0" + item.centi.toString() + " €")
        } else {
            priceTV.setText(item.eiro.toString() + "." + item.centi.toString() + " €")
        }
    }

    private fun editItem() {
        val intent = Intent(this!!, EditItemActivity()::class.java)
        intent.putExtra("iId", item.id)
        intent.putExtra("id", confectioneryId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(this!!, intent, null)
    }
}
