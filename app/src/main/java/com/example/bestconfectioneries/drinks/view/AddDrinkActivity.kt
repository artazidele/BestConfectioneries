package com.example.bestconfectioneries.drinks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.helpers.Navigation

class AddDrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drink)


//        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> Navigation().fromTo(this, DrinkListActivity())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Navigation().fromTo(this, DrinkListActivity())
    }
}