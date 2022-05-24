package com.example.bestconfectioneries


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.bestconfectioneries.confectioneries.view.AllConfectioneryListActivity
import com.example.bestconfectioneries.drinks.view.AllDrinkActivity
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.view.AllItemActivity
import com.example.bestconfectioneries.items.view.ItemListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drinks -> Navigation().fromTo(this, AllDrinkActivity())
            R.id.items -> Navigation().fromTo(this, AllItemActivity())
            R.id.confectioneries -> Navigation().fromTo(this, AllConfectioneryListActivity())
            R.id.confectionery_drinks -> Navigation().fromTo(this, DrinkListActivity())
            R.id.confectionery_items -> Navigation().fromTo(this, ItemListActivity())
        }
        return super.onOptionsItemSelected(item)
    }


}