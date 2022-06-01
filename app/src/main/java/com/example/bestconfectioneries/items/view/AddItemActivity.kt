package com.example.bestconfectioneries.items.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.databinding.ActivityAddItemBinding
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.drinks.viewmodel.DrinkViewModel
import com.example.bestconfectioneries.helpers.ErrorHandling
import com.example.bestconfectioneries.helpers.Navigation
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var newItem: Item
    private lateinit var addDrinkBtn: Button
    private lateinit var nameET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var cookiesRB: RadioButton
    private lateinit var bunRB: RadioButton
    private lateinit var cakeRB: RadioButton
    private lateinit var weightET: EditText
    private lateinit var allergensET: EditText
    private lateinit var vegansCB: CheckBox
    private lateinit var vegetariansCB: CheckBox
    private lateinit var eiroET: EditText
    private lateinit var centiET: EditText
    private lateinit var addImageBtn: Button
    private lateinit var confectioneryId: String
    lateinit var itemImageView: ImageView
    private val pickItemImage = 100
    private var itemImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("New Item")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        confectioneryId = intent.getStringExtra("id").toString()
        itemImageView = binding.itemIv
        nameET = binding.drinkNameEt
        descriptionET = binding.drinkDescriptionEt
        cookiesRB = binding.cookiesRb
        bunRB = binding.bunRb
        cakeRB = binding.cakeRb
        weightET = binding.drinkCapacityEt
        allergensET = binding.drinkAllergensEt
        vegansCB = binding.vegans
        vegetariansCB = binding.vegetarians
        eiroET = binding.eiroEt
        centiET = binding.centiEt
        addDrinkBtn = binding.addDrinkButton
        addDrinkBtn.visibility = View.GONE
        itemImageView.visibility = View.GONE
        addDrinkBtn.setOnClickListener {
            addDrink()
        }
        addImageBtn = binding.addImageButton
        addImageBtn.setOnClickListener {
            addImage()
        }
    }

    private fun addImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, 1001)
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickItemImage)
            }
        } else {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent, pickItemImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickItemImage) {
            itemImageUri = data?.data
            itemImageView.setImageURI(itemImageUri)
            itemImageView.visibility = View.VISIBLE
            addDrinkBtn.visibility = View.VISIBLE
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
        dialogView.findViewById<TextView>(R.id.question_tv).text = "If You go back, new item won't be saved. \n Do You want to go back?"
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
            Navigation().fromToStringId(this, ItemListActivity(), confectioneryId)
        }
    }

    private fun addDrink() {
        if (cakeRB.isChecked == false && bunRB.isChecked == false && cookiesRB.isChecked == false) {
            val message = "Please, choose item type!"
            ErrorHandling().showErrorWindow(this, message)
        } else {
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
            } else if (centiET.text.toString().length < 2) {
                centiET.setText(centiET.text.toString() + "0")
            }
            if (weightET.text.toString() == "") {
                weightET.setText("0")
            }
            val totalPrice = eiroET.text.toString().toInt() * 100 + centiET.text.toString().toInt()
            val centi = totalPrice % 100
            val eiro = (totalPrice - centi) / 100
            newItem = Item(
                id,
                confectioneryId,
                nameET.text.toString(),
                cakeRB.isChecked,
                bunRB.isChecked,
                cookiesRB.isChecked,
                eiro,
                centi,
                weightET.text.toString().toInt(),
                descriptionET.text.toString(),
                editedBy, editedOn,
                vegetariansCB.isChecked,
                vegansCB.isChecked,
                allergensET.text.toString(),
            )
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            addDrinkBtn.isEnabled = false
            addDrinkBtn.text = "Loading..."
            addImageBtn.isEnabled = false
            viewModel.addImage(newItem.id, itemImageView) { added ->
                if (added == true) {
                    viewModel.addNewItem(newItem) { itemAdded ->
                        if (itemAdded == true) {
                            Navigation().fromToStringId(this, ItemListActivity(), confectioneryId)
                        } else {
                            val message = "Something went wrong. \n Please, try again later!"
                            ErrorHandling().showErrorWindow(this, message)
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            addDrinkBtn.isEnabled = true
                            addDrinkBtn.text = "ADD NEW ITEM"
                            addImageBtn.isEnabled = true
                        }
                    }
                } else {
                    val message = "Something went wrong. \n Please, try again later!"
                    ErrorHandling().showErrorWindow(this, message)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    addDrinkBtn.isEnabled = true
                    addDrinkBtn.text = "ADD NEW ITEM"
                    addImageBtn.isEnabled = true
                }

            }
        }
    }
}
