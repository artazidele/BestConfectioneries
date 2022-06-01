package com.example.bestconfectioneries.confectioneries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.model.Confectionery
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryViewModel
import com.example.bestconfectioneries.databinding.ActivityOneConfectioneryBinding
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.view.ItemListActivity

class OneConfectioneryActivity : AppCompatActivity() {
    private val viewModel: ConfectioneryViewModel by viewModels()
    private lateinit var binding: ActivityOneConfectioneryBinding
    private lateinit var confectioneryId: String
    private lateinit var confectionery: Confectionery
    private lateinit var menuItem: MenuItem
    private lateinit var nameTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var addressTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var phoneTV: TextView
    private lateinit var editMenuItem: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneConfectioneryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Confectionery")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        confectioneryId = intent.getStringExtra("id").toString()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        nameTV = binding.confName
        descriptionTV = binding.confDescription
        addressTV = binding.confAddress
        emailTV = binding.confEmail
        phoneTV = binding.confPhone
        getConfectionery()
        binding.tryBtn.setOnClickListener {
            getConfectionery()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.one_confectionery_menu, menu)
        menuItem = menu!!.findItem(R.id.find)
        editMenuItem = menu!!.findItem(R.id.edit)
        menuItem.isEnabled = false
        editMenuItem.isEnabled = false
        editMenuItem.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> Navigation().fromToStringId(this, EditConfectioneryActivity(), confectioneryId)
            R.id.confectionery_drinks -> Navigation().fromToStringId(this, DrinkListActivity(), confectioneryId)
            R.id.confectionery_items -> Navigation().fromToStringId(this, ItemListActivity(), confectioneryId)
            R.id.find -> viewModel.getAddress(this, confectionery.address)
            else -> Navigation().fromTo(this, AllConfectioneryListActivity())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getConfectionery() {
        val userId = "OwnerI" // Get curent user id "OwnerId"
        viewModel.getOneConfectionery(confectioneryId) {
            if (it?.id != null) {
                confectionery = it!!
                menuItem.isEnabled = true
                if (confectionery.ownerId == userId) {
                    editMenuItem.isEnabled = true
                    editMenuItem.isVisible = true
                }
                updateUI()
            }
        }

    }

    private fun updateUI() {
        nameTV.setText(confectionery.name)
        descriptionTV.setText(confectionery.description)
        addressTV.setText(confectionery.address)
        emailTV.setText(confectionery.email)
        phoneTV.setText(confectionery.phone)
    }
}