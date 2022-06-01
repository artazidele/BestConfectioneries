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
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.bestconfectioneries.R
import com.example.bestconfectioneries.confectioneries.view.OneConfectioneryActivity
import com.example.bestconfectioneries.databinding.ActivityAddItemBinding
import com.example.bestconfectioneries.databinding.ActivityEditItemBinding
import com.example.bestconfectioneries.databinding.ActivityItemBinding
import com.example.bestconfectioneries.drinks.view.DrinkListActivity
import com.example.bestconfectioneries.helpers.ErrorHandling
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.viewmodel.ItemViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EditItemActivity : AppCompatActivity() {
    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityEditItemBinding

    private lateinit var itemId: String
    private lateinit var confectioneryId: String

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
    private lateinit var deleteItemBtn: Button
    lateinit var itemImageView: ImageView
    private val pickItemImage = 100
    private var itemImageUri: Uri? = null
    private lateinit var imageURL: String
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditItemBinding.inflate(layoutInflater)
        itemId = intent.getStringExtra("iId").toString()
        confectioneryId = intent.getStringExtra("id").toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        setTitle("Edit Item")
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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
        deleteItemBtn = binding.deleteItemButton
        deleteItemBtn.setOnClickListener {
            openDeleteWindow()
        }
        getItem()
        addDrinkBtn.setOnClickListener {
            editItem()
        }
        binding.tryBtn.setOnClickListener {
            getItem()
        }
        addImageBtn = binding.addImageButton
        addImageBtn.setOnClickListener {
            changeImage()
        }
    }

    private fun openDeleteWindow() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.question_layout, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = item.name
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
            deleteItem()
        }
    }

    private fun getItem() {
        viewModel.getImage(itemId) {
            if (it?.length != 0) {
                imageURL = it!!
                viewModel.getOneItem(itemId) {
                    if (it?.id != null) {
                        item = it
                        updateUI()
                    }
                }
            }
        }

    }

    private fun updateUI() {
        nameET.setText(item.name)
        Glide.with(this@EditItemActivity)
            .load(imageURL)
            .into(itemImageView)
        if (item.bun == true) {
            bunRB.isChecked = true
        } else if (item.cake == true) {
            cakeRB.isChecked = true
        } else {
            cookiesRB.isChecked = true
        }
        descriptionET.setText(item.description)
        allergensET.setText(item.allergens)
        if (item.forVegans == false) {
            vegansCB.isChecked = true
        }
        if (item.forVegetarians == false) {
            vegetariansCB.isChecked = true
        }
        weightET.setText(item.weight.toString())
        eiroET.setText(item.eiro.toString())
        if (item.centi < 0) {
            centiET.setText("0" + item.centi.toString())
        } else {
            centiET.setText(item.centi.toString())
        }
    }

    private fun changeImage() {
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
        dialogView.findViewById<TextView>(R.id.drink_name_tv).text = item.name
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
            val intent = Intent(this!!, ItemActivity()::class.java)
            intent.putExtra("iId", item.id)
            intent.putExtra("id", confectioneryId)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            ContextCompat.startActivity(this!!, intent, null)
        }
    }

    private fun editItem() {
        val confectionerId = "ConfectionerId" // Get current user id or etc
        val editedBy = item.editedBy
        val editedOn = item.editedOn
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
        if (weightET.text.toString() == "") {
            weightET.setText("0")
        }
        val totalPrice = eiroET.text.toString().toInt() * 100 + centiET.text.toString().toInt()
        val centi = totalPrice % 100
        val eiro = (totalPrice - centi) / 100
        val newItem = Item(
            itemId,
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
        deleteItemBtn.isEnabled = false
        addImageBtn.isEnabled = false
        viewModel.addImage(newItem.id, itemImageView) { added ->
            if (added == true) {
                viewModel.updateOneItem(newItem) { itemAdded ->
                    if (itemAdded == true) {
                        val intent = Intent(this!!, ItemActivity()::class.java)
                        intent.putExtra("iId", item.id)
                        intent.putExtra("id", confectioneryId)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ContextCompat.startActivity(this!!, intent, null)
                    } else {
                        val message = "Something went wrong. \n Please, try again later!"
                        ErrorHandling().showErrorWindow(this, message)
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        addDrinkBtn.isEnabled = true
                        addDrinkBtn.text = "EDIT ITEM"
                        addImageBtn.isEnabled = true
                        deleteItemBtn.isEnabled = true
                    }
                }
            } else {
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addDrinkBtn.isEnabled = true
                addDrinkBtn.text = "EDIT ITEM"
                addImageBtn.isEnabled = true
                deleteItemBtn.isEnabled = true
            }
        }
    }

    private fun deleteItem() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        addDrinkBtn.isEnabled = false
        deleteItemBtn.text = "Loading..."
        deleteItemBtn.isEnabled = false
        addImageBtn.isEnabled = false
        viewModel.deleteOneItem(itemId) { itemAdded ->
            if (itemAdded == true) {
                com.example.bestconfectioneries.helpers.Navigation()
                    .fromToStringId(this, ItemListActivity(), confectioneryId)
            } else {
                val message = "Something went wrong. \n Please, try again later!"
                ErrorHandling().showErrorWindow(this, message)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                addDrinkBtn.isEnabled = true
                addDrinkBtn.text = "EDIT ITEM"
                addImageBtn.isEnabled = true
                deleteItemBtn.isEnabled = true
            }
        }

    }
}
