package com.example.bestconfectioneries.confectioneries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.model.Confectionery
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryViewModel
import com.example.bestconfectioneries.databinding.ActivityAddConfectioneryBinding
import com.example.bestconfectioneries.databinding.ActivityEditConfectioneryBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.helpers.ErrorHandling
import com.example.bestconfectioneries.helpers.Navigation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EditConfectioneryActivity : AppCompatActivity() {
    private val viewModel: ConfectioneryViewModel by viewModels()
    private lateinit var binding: ActivityEditConfectioneryBinding
    private lateinit var menuDeleteItem: MenuItem
    private lateinit var addConfectioneryBtn: Button
    private lateinit var nameET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var addressET: EditText
    private lateinit var emailET: EditText
    private lateinit var phoneET: EditText
    private lateinit var confectionery: Confectionery
    private lateinit var confectioneryId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditConfectioneryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Edit Confectionery")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        confectioneryId = intent.getStringExtra("id").toString()
        nameET = binding.confEt
        descriptionET = binding.confDescriptionEt
        addressET = binding.confAddressEt
        emailET = binding.confEmailEt
        phoneET = binding.confPhoneEt
        addConfectioneryBtn = binding.addConfButton
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        getConfectionery()
        binding.tryBtn.setOnClickListener {
            getConfectionery()
        }
        addConfectioneryBtn.setOnClickListener {
            updateConfectionery()
        }
    }

    private fun getConfectionery() {
        viewModel.getOneConfectionery(confectioneryId) {
            if (it?.id != null) {
                confectionery = it!!
                updateUI()
            }
        }
    }

    private fun updateUI() {
        menuDeleteItem.isEnabled = true
        nameET.setText(confectionery.name)
        descriptionET.setText(confectionery.description)
        addressET.setText(confectionery.address)
        emailET.setText(confectionery.email)
        phoneET.setText(confectionery.phone)
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

    private fun openDeleteWindow() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = confectionery.name
//        val confectionerId = "ConfectionerId" // Get current user id or etc // OwnerId to allow delete
//        if (confectionerId == confectionery.ownerId) {
        dialogView.findViewById<TextView>(R.id.question_tv).text =
            "Do You want to delete this confectionery?"
        dialogView.findViewById<Button>(R.id.close_window_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.not_delete_drink_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.delete_drink_button).setOnClickListener {
            alertDialog.dismiss()
            deleteConfectionery()
        }
//        } else {
//        dialogView.findViewById<TextView>(R.id.question_tv).text =
//            "You are not able to delete this confectionery, because You are not its owner."
//        dialogView.findViewById<Button>(R.id.close_window_button).setOnClickListener {
//            alertDialog.dismiss()
//        }
//        dialogView.findViewById<Button>(R.id.not_delete_drink_button).visibility = View.GONE
//        dialogView.findViewById<Button>(R.id.delete_drink_button).visibility = View.GONE
//        }
    }

    private fun deleteConfectionery() {
        addConfectioneryBtn.isEnabled = false
        menuDeleteItem.isEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.deleteOneConfectionery(confectioneryId) { deleted ->
            if (deleted == true) {
                Navigation().fromTo(this, AllConfectioneryListActivity())
            } else {
                menuDeleteItem.isEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addConfectioneryBtn.isEnabled = true
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
            }
        }
    }

    private fun openBackWindow() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = nameET.text.toString()
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
            Navigation().fromToStringId(this, OneConfectioneryActivity(), confectioneryId)
        }
    }

    private fun updateConfectionery() {
        val confectionerId = "ConfectionerId" // Get current user id or etc
        val editedBy = confectionery.editedBy
        val editedOn = confectionery.editedOn
        val dateAndTimeNow = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
        editedBy.add(confectionerId)
        editedOn.add(dateAndTimeToSave)
        val updatedConfectionery = Confectionery(
            confectioneryId,
            nameET.text.toString(),
            descriptionET.text.toString(),
            addressET.text.toString(),
            emailET.text.toString(),
            phoneET.text.toString(),
            confectionery.ownerId,
            editedBy, editedOn,
            confectionery.photosId
        )
        menuDeleteItem.isEnabled = false
        addConfectioneryBtn.text = "Loading..."
        addConfectioneryBtn.isEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        viewModel.updateOneConfectionery(updatedConfectionery) { updated ->
            if (updated == true) {
                Navigation().fromToStringId(this, OneConfectioneryActivity(), confectioneryId)
            } else {
                menuDeleteItem.isEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addConfectioneryBtn.text = "UPDATE CONFECTIONERY"
                addConfectioneryBtn.isEnabled = false
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
            }
        }
    }
}
