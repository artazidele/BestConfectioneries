package com.example.bestconfectioneries.drinks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestconfectioneries.drinks.model.Drink
import com.example.bestconfectioneries.drinks.model.DrinkDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

enum class DrinkNetworkStatus { LOADING, ERROR, DONE }

class DrinkViewModel : ViewModel() {
    private val _status = MutableLiveData<DrinkNetworkStatus>()
    val status: LiveData<DrinkNetworkStatus> = _status

    fun addNewDrink(drink: Drink, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = DrinkNetworkStatus.LOADING
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
}
