package com.example.bestconfectioneries.confectioneries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestconfectioneries.confectioneries.model.Confectionery
import com.example.bestconfectioneries.confectioneries.model.ConfectioneryDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch


enum class ConfectioneryNetworkStatus { LOADING, ERROR, DONE }

class ConfectioneryViewModel : ViewModel() {
    private val _status = MutableLiveData<ConfectioneryNetworkStatus>()
    val status: LiveData<ConfectioneryNetworkStatus> = _status

    fun addNewConfectionery(confectionery: Confectionery, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            ConfectioneryDatabase().addConfectionery(confectionery)
                .addOnSuccessListener { documents ->
                    onResult(true)
                }
                .addOnFailureListener {
                    onResult(false)
                }
        }
    }

    fun getOneConfectionery(id: String, onResult: (Confectionery?) -> Unit) {
        viewModelScope.launch {
            _status.value = ConfectioneryNetworkStatus.LOADING
            ConfectioneryDatabase().getConfectionery(id)
                .addOnSuccessListener { document ->
                    val confectionery = document.toObject<Confectionery>()
                    if (confectionery?.id != null) {
                        _status.value = ConfectioneryNetworkStatus.DONE
                        onResult(confectionery)
                    } else {
                        _status.value = ConfectioneryNetworkStatus.ERROR
                        onResult(null)
                    }

                }
                .addOnFailureListener {
                    _status.value = ConfectioneryNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun deleteOneConfectionery(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = ConfectioneryNetworkStatus.LOADING
            ConfectioneryDatabase().deleteConfectionery(id)
                .addOnSuccessListener {
                    _status.value = ConfectioneryNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = ConfectioneryNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun updateOneConfectionery(confectionery: Confectionery, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = ConfectioneryNetworkStatus.LOADING
            ConfectioneryDatabase().updateConfectionery(confectionery)
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = ConfectioneryNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun getAllConfectioneries(onResult: (ArrayList<Confectionery>?) -> Unit) {
        viewModelScope.launch {
            var confectioneryList = ArrayList<Confectionery>()
            _status.value = ConfectioneryNetworkStatus.LOADING
            ConfectioneryDatabase().getConfectioneries()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val confectionery = document.toObject<Confectionery>()
                        confectioneryList.add(confectionery)
                    }
                    _status.value = ConfectioneryNetworkStatus.DONE
                    onResult(confectioneryList)
                }
                .addOnFailureListener {
                    _status.value = ConfectioneryNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }
}
