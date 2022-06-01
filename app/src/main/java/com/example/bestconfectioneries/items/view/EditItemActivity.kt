package com.example.bestconfectioneries.items.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.view.OneConfectioneryActivity
import com.example.bestconfectioneries.items.model.Item

class EditItemActivity : AppCompatActivity() {
    private lateinit var itemId: String
    private lateinit var confectioneryId: String
    override fun onCreate(savedInstanceState: Bundle?) {

        itemId = intent.getStringExtra("iId").toString()
        confectioneryId = intent.getStringExtra("id").toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> com.example.bestconfectioneries.helpers.Navigation()
                .fromToStringId(this, ItemListActivity(), confectioneryId)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editItem(item: Item) {
        val intent = Intent(this!!, ItemActivity()::class.java)
        intent.putExtra("iId", item.id)
        intent.putExtra("id", confectioneryId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(this!!, intent, null)
    }

    private fun deleteItem(item: Item) {
        com.example.bestconfectioneries.helpers.Navigation()
            .fromToStringId(this, ItemListActivity(), confectioneryId)
    }
}