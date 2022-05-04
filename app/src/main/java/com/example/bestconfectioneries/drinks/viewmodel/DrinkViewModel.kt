package com.example.bestconfectioneries.drinks.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.model.DrinkDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

enum class DrinkNetworkStatus { LOADING, ERROR, DONE }

class DrinkViewModel : ViewModel() {
    private val _status = MutableLiveData<DrinkNetworkStatus>()
    val status: LiveData<DrinkNetworkStatus> = _status
    fun addNewDrink(drink: Drink,onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            DrinkDatabase().addDrink(drink)
                .addOnSuccessListener { documents ->
                    onResult(true)
                }
                .addOnFailureListener {
                    onResult(false)
                }
        }
    }

    fun getOneDrink(id: String, onResult: (Drink?) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING //DONE //LOADING
            DrinkDatabase().getDrink(id)
                .addOnSuccessListener { document ->
                    val drink = document.toObject<Drink>()
                    if (drink?.id != null) {
                        _status.value = DrinkNetworkStatus.DONE
                        onResult(drink)
                    } else {
                        _status.value = DrinkNetworkStatus.ERROR
                        onResult(null)
                    }

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
                    _status.value = DrinkNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun updateOneDrink(drink: Drink, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING
            DrinkDatabase().updateDrink(drink)
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = DrinkNetworkStatus.DONE
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
