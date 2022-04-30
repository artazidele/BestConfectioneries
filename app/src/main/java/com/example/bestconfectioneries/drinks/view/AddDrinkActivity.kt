package com.example.bestconfectioneries.drinks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import com.example.bestconfectioneries.MainActivity
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityAddDrinkBinding
import com.example.bestconfectioneries.databinding.ActivityDrinkListBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.helpers.Network
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddDrinkActivity : AppCompatActivity() {
    private val viewModel: DrinkViewModel by viewModels()
    private lateinit var binding: ActivityAddDrinkBinding

    private lateinit var nameET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var coffeeRB: RadioButton
    private lateinit var teaRB: RadioButton
    private lateinit var otherRB: RadioButton
    private lateinit var capacityET: EditText
    private lateinit var eiroET: EditText
    private lateinit var centiET: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("New Drink")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nameET = binding.drinkNameEt
        descriptionET = binding.drinkDescriptionEt
        coffeeRB = binding.coffeeRb
        teaRB = binding.teaRb
        otherRB = binding.otherRb
        capacityET = binding.drinkCapacityEt
        eiroET = binding.eiroEt
        centiET = binding.centiEt

        binding.addDrinkButton.setOnClickListener {
            if (Network().checkConnection(this) == true) {
                addDrink()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> Navigation().fromTo(this, DrinkListActivity())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addDrink() {
        val confectionerId = "ConfectionerId" // Get current user id or etc
        val confectioneryId = "1" // Get current user's confectionery id or etc
        val editedBy: ArrayList<String> = ArrayList()
        val editedOn: ArrayList<String> = ArrayList()
        val dateAndTimeNow = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
        editedBy.add(confectionerId)
        editedOn.add(dateAndTimeToSave)
        val uuid = UUID.randomUUID()
        val id = uuid.toString()
        if (eiroET.text.toString() == "") {
            eiroET.setText("0")
        }
        if (centiET.text.toString() == "") {
            centiET.setText("0")
        }
        if (capacityET.text.toString() == "") {
            capacityET.setText("0")
        }
        val totalPrice = eiroET.text.toString().toInt() * 100 + centiET.text.toString().toInt()
        val centi = totalPrice % 100
        val eiro = (totalPrice - centi) / 100
        val newDrink = Drink(
            id,
            confectioneryId,
            nameET.text.toString(),
            coffeeRB.isChecked,
            teaRB.isChecked,
            otherRB.isChecked,
            eiro,
            centi,
            capacityET.text.toString().toInt(),
            descriptionET.text.toString(),
            editedBy, editedOn
        )
        viewModel.addNewDrink(newDrink) { added ->
            if (added == true) {
                Navigation().fromTo(this, DrinkListActivity())
            } else {

            }
        }

    }
}