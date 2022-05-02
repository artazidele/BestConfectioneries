package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.viewModels
import com.example.bestconfectioneries.databinding.ActivityEditDrinkBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.helpers.Network
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditDrinkActivity : AppCompatActivity() {
    private val viewModel: DrinkViewModel by viewModels()
    private lateinit var binding: ActivityEditDrinkBinding

    private lateinit var nameET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var coffeeRB: RadioButton
    private lateinit var teaRB: RadioButton
    private lateinit var otherRB: RadioButton
    private lateinit var capacityET: EditText
    private lateinit var eiroET: EditText
    private lateinit var centiET: EditText
    private lateinit var editDrinkBtn: Button

    private lateinit var drinkId: String
    private lateinit var drink: Drink
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Edit Drink")


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drinkId = intent.getStringExtra("id").toString()
        nameET = binding.drinkNameEt
        descriptionET = binding.drinkDescriptionEt
        coffeeRB = binding.coffeeRb
        teaRB = binding.teaRb
        otherRB = binding.otherRb
        capacityET = binding.drinkCapacityEt
        eiroET = binding.eiroEt
        centiET = binding.centiEt
        editDrinkBtn = binding.addDrinkButton

        getDrink()
    }

    private fun getDrink() {
        viewModel.getOneDrink(drinkId!!) {
            if (it?.id != null) {
                drink = it
                updateUI()
                Log.d(ContentValues.TAG, "FOUND")
            } else {
                reloadActivity()
                Log.d(ContentValues.TAG, "NOT FOUND")
            }
        }
    }

    private fun updateUI() {
        nameET.setText(drink.name)
        descriptionET.setText(drink.description)
        capacityET.setText(drink.capacity.toString())
        eiroET.setText(drink.eiro.toString())
        centiET.setText(drink.centi.toString())
        if (drink.coffee == true) {
            coffeeRB.isChecked = true
        } else if (drink.tea == true) {
            teaRB.isChecked = true
        } else {
            otherRB.isChecked = true
        }
        editDrinkBtn.setOnClickListener {
            if (Network().checkConnection(this) == true) {
                editDrink()
            }
        }
    }

    private fun editDrink() {
        val confectionerId = "ConfectionerId" // Get current user id or etc
        val confectioneryId = "1" // Get current user's confectionery id or etc
        val editedBy = drink.editedBy
        val editedOn = drink.editedOn
        val dateAndTimeNow = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
        editedBy.add(confectionerId)
        editedOn.add(dateAndTimeToSave)
        if (eiroET.text.toString() == "") {
            eiroET.setText("0")
        }
        if (centiET.text.toString() == "") {
            centiET.setText("0")
        } else if (centiET.text.toString().length < 2) {
            centiET.setText(centiET.text.toString() + "0")
        }
        if (capacityET.text.toString() == "") {
            capacityET.setText("0")
        }
        val totalPrice = eiroET.text.toString().toInt() * 100 + centiET.text.toString().toInt()
        val centi = totalPrice % 100
        val eiro = (totalPrice - centi) / 100
        val newDrink = Drink(
            drinkId!!,
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
        viewModel.updateOneDrink(newDrink) { added ->
            if (added == true) {
                Navigation().fromTo(this, DrinkListActivity())
            } else {

            }
        }
    }

    private fun reloadActivity() {
        // dialogview with button refresh
    }
}