package com.example.bestconfectioneries

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.helpers.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drinks -> Navigation().fromTo(this, DrinkListActivity())
//            R.id.exit -> fromTo(this, DrinkListActivity())
//            R.id.profile -> fromTo(this, DrinkListActivity())
        }
        return super.onOptionsItemSelected(item)
    }


}