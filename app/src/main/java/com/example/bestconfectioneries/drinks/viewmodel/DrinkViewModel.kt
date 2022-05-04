package com.example.bestconfectioneries.drinks.viewmodel

import android.widget.EditText
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.model.DrinkDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

enum class DrinkNetworkStatus { LOADING, ERROR, DONE }

class DrinkViewModel : ViewModel() {
    private val _status = MutableLiveData<DrinkNetworkStatus>()
    val status: LiveData<DrinkNetworkStatus> = _status

    fun addNewDrink(
        drink: Drink,
//        nameET: EditText,
//        coffeeRB: RadioButton,
//        teaRB: RadioButton,
//        otherRB: RadioButton,
//        capacityET: EditText,
//        descriptionET: EditText,
//        eiroET: EditText,
//        centiET: EditText,
        onResult: (Boolean) -> Unit
    ) {
//    fun addNewDrink(drink: Drink) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING


//            val confectionerId = "ConfectionerId" // Get current user id or etc
//            val confectioneryId = "1" // Get current user's confectionery id or etc
//            val editedBy: ArrayList<String> = ArrayList()
//            val editedOn: ArrayList<String> = ArrayList()
//            val dateAndTimeNow = LocalDateTime.now()
//            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//            val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
//            editedBy.add(confectionerId)
//            editedOn.add(dateAndTimeToSave)
//            val uuid = UUID.randomUUID()
//            val id = uuid.toString()
//            if (eiroET.text.toString() == "") {
//                eiroET.setText("0")
//            }
//            if (centiET.text.toString() == "") {
//                centiET.setText("0")
//            } else if (centiET.text.toString().length < 2) {
//                centiET.setText(centiET.text.toString() + "0")
//            }
//            if (capacityET.text.toString() == "") {
//                capacityET.setText("0")
//            }
//            val totalPrice = eiroET.text.toString().toInt() * 100 + centiET.text.toString().toInt()
//            val centi = totalPrice % 100
//            val eiro = (totalPrice - centi) / 100
//            val newDrink = Drink(
//                id,
//                confectioneryId,
//                nameET.text.toString(),
//                coffeeRB.isChecked,
//                teaRB.isChecked,
//                otherRB.isChecked,
//                eiro,
//                centi,
//                capacityET.text.toString().toInt(),
//                descriptionET.text.toString(),
//                editedBy, editedOn
//            )



            DrinkDatabase().addDrink(drink)
                .addOnSuccessListener { documents ->
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun getOneDrink(id: String, onResult: (Drink?) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().getDrink(id)
                .addOnSuccessListener { document ->
                    _status.value = DrinkNetworkStatus.DONE
                    val drink = document.toObject<Drink>()
                    onResult(drink)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun deleteOneDrink(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().deleteDrink(id)
                .addOnSuccessListener {
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun updateOneDrink(drink: Drink, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().updateDrink(drink)
                .addOnSuccessListener {
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun getAllDrinks(onResult: (ArrayList<Drink>?) -> Unit) {
        viewModelScope.launch {
            var drinkList = ArrayList<Drink>()
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().getDrinks()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val drink = document.toObject<Drink>()
                        drinkList.add(drink)
                    }
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(drinkList)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun getThisConfectioneryDrinks(confectioneryId: String, onResult: (ArrayList<Drink>?) -> Unit) {
        viewModelScope.launch {
            var drinkList = ArrayList<Drink>()
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().getOneConfectioneryDrinks(confectioneryId)
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val drink = document.toObject<Drink>()
                        drinkList.add(drink)
                    }
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(drinkList)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }
}
