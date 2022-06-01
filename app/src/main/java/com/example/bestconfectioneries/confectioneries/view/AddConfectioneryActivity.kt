package com.example.bestconfectioneries.confectioneries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.model.Confectionery
import com.example.bestconfectioneries.confectioneries.viewmodel.ConfectioneryViewModel
import com.example.bestconfectioneries.databinding.ActivityAddConfectioneryBinding
import com.example.bestconfectioneries.helpers.ErrorHandling
import com.example.bestconfectioneries.helpers.Navigation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddConfectioneryActivity : AppCompatActivity() {
    private val viewModel: ConfectioneryViewModel by viewModels()
    private lateinit var binding: ActivityAddConfectioneryBinding
    private lateinit var addConfectioneryBtn: Button
    private lateinit var nameET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var addressET: EditText
    private lateinit var emailET: EditText
    private lateinit var phoneET: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddConfectioneryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("New Confectionery")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nameET = binding.confEt
        descriptionET = binding.confDescriptionEt
        addressET = binding.confAddressEt
        emailET = binding.confEmailEt
        phoneET = binding.confPhoneEt
        addConfectioneryBtn = binding.addConfButton
        addConfectioneryBtn.setOnClickListener {
            addConfectionery()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> openBackWindow()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openBackWindow() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = nameET.text.toString()
        dialogView.findViewById<TextView>(R.id.question_tv).text =
            "If You go back, new confectionery won't be saved. \n Do You want to go back?"
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
            Navigation().fromTo(this, AllConfectioneryListActivity())
        }
    }

    private fun addConfectionery() {
        val ownerId = "OwnerId" // Get current user id or etc
        val uuid = UUID.randomUUID()
        val id = uuid.toString()
        val photosId: ArrayList<String> = ArrayList()
        val editedBy: ArrayList<String> = ArrayList()
        val editedOn: ArrayList<String> = ArrayList()
        val dateAndTimeNow = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
        editedBy.add(ownerId)
        editedOn.add(dateAndTimeToSave)
        val newConfectionery = Confectionery(
            id,
            nameET.text.toString(),
            descriptionET.text.toString(),
            addressET.text.toString(),
            emailET.text.toString(),
            phoneET.text.toString(),
            ownerId,
            photosId
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        addConfectioneryBtn.isEnabled = false
        addConfectioneryBtn.text = "Loading..."
        viewModel.addNewConfectionery(newConfectionery) { added ->
            if (added == true) {
                Navigation().fromTo(this, AllConfectioneryListActivity())
            } else {
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addConfectioneryBtn.isEnabled = true
                addConfectioneryBtn.text = "ADD NEW CONFECTIONERY"
            }
        }
    }
}
