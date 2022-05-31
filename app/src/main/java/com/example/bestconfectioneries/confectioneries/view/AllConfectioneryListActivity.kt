package com.example.bestconfectioneries.confectioneries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryViewModel

class AllConfectioneryListActivity : AppCompatActivity() {
    private lateinit var getAddressBtn: Button
    private lateinit var getLocationBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_confectionery_list)
        getAddressBtn = findViewById(R.id.set_location)
        getLocationBtn = findViewById(R.id.get_location)
        getLocationBtn.setOnClickListener {
            ConfectioneryViewModel().getLocation(this, "56.95", "24.1")
        }
        getAddressBtn.setOnClickListener {
            ConfectioneryViewModel().getAddress(this, "Riga")
        }
    }
}