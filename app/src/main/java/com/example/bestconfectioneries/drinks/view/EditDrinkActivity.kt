package com.example.bestconfectioneries.drinks.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityEditDrinkBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.ErrorHandling
import com.example.bestconfectioneries.helpers.Navigation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditDrinkActivity : AppCompatActivity() {
    private val viewModel: DrinkViewModel by viewModels()
    private lateinit var binding: ActivityEditDrinkBinding
    private lateinit var menuDeleteItem: MenuItem
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        getDrink()
        binding.tryBtn.setOnClickListener {
            getDrink()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_drink_menu, menu)
        menuDeleteItem = menu!!.findItem(R.id.delete_drink)
        menuDeleteItem.isEnabled = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_drink -> openDeleteWindow()
            else -> openBackWindow()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openBackWindow() {
        if (menuDeleteItem.isEnabled == true) {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
            val alertDialog = builder.show()
            dialogView.findViewById<TextView>(R.id.drink_name_tv).text = drink.name
            dialogView.findViewById<TextView>(R.id.question_tv).text =
                "If You go back, changes won't be saved. \n Do You want to go back?"
            dialogView.findViewById<Button>(R.id.not_delete_drink_button).text = "No, stay here"
            dialogView.findViewById<Button>(R.id.delete_drink_button).text = "Yes, go back"
            dialogView.findViewById<Button>(R.id.close_window_button).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.not_delete_drink_button).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.delete_drink_button).setOnClickListener {
                alertDialog.dismiss()
                Navigation().fromTo(this, DrinkListActivity())
            }
        } else {
            Navigation().fromTo(this, DrinkListActivity())
        }
    }

    private fun openDeleteWindow() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = drink.name
        dialogView.findViewById<TextView>(R.id.question_tv).text =
            "Do You want to delete this drink?"
        dialogView.findViewById<Button>(R.id.close_window_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.not_delete_drink_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.delete_drink_button).setOnClickListener {
            alertDialog.dismiss()
            deleteDrink()
        }
    }

    private fun deleteDrink() {
        menuDeleteItem.isEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.deleteOneDrink(drinkId) { deleted ->
            if (deleted == true) {
                Navigation().fromTo(this, DrinkListActivity())
            } else {
                menuDeleteItem.isEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
            }
        }
    }

    private fun getDrink() {
        viewModel.getOneDrink(drinkId!!) { // viewModel.getOneDrink("5") {
            if (it?.id != null) {
                drink = it
                updateUI()
                Log.d(ContentValues.TAG, "FOUND")
            }
        }
    }

    private fun updateUI() {
        menuDeleteItem.isEnabled = true
        nameET.setText(drink.name)
        descriptionET.setText(drink.description)
        capacityET.setText(drink.capacity.toString())
        eiroET.setText(drink.eiro.toString())
        if (drink.centi < 10) {
            centiET.setText("0" + drink.centi.toString())
        } else {
            centiET.setText(drink.centi.toString())
        }
        if (drink.coffee == true) {
            coffeeRB.isChecked = true
        } else if (drink.tea == true) {
            teaRB.isChecked = true
        } else {
            otherRB.isChecked = true
        }
        editDrinkBtn.setOnClickListener {
            editDrink()
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
            drinkId!!, // "5"
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
        menuDeleteItem.isEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.updateOneDrink(newDrink) { added ->
            if (added == true) {
                Navigation().fromTo(this, DrinkListActivity())
            } else {
                menuDeleteItem.isEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
            }
        }
    }
}
