package com.example.bestconfectioneries.confectioneries.viewmodel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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

//    fun getLocation(context: Context, latitude: String, longitude: String) {
//        val location =
//            Uri.parse("geo:0,0?q=" + latitude + ", " + longitude)
//        val mapIntent = Intent(Intent.ACTION_VIEW, location)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        try {
//            startActivity(context, mapIntent, null)
//        } catch (e: ActivityNotFoundException) {
//            Log.d("GoogleMaps connecting error: ", e.localizedMessage)
//        }
//    }

    fun getAddress(context: Context, address: String) {
        var addressString = ""
        val addressArray = address.toCharArray()
        for (char in addressArray) {
            if (char == ' '){
                addressString += "%20"
            } else {
                addressString += char
            }
        }
        val location =
            Uri.parse("geo:0,0?q=" + addressString)
        val mapIntent = Intent(Intent.ACTION_VIEW, location)
        mapIntent.setPackage("com.google.android.apps.maps")
        try {
            startActivity(context, mapIntent, null)
        } catch (e: ActivityNotFoundException) {
            Log.d("GoogleMaps connecting error: ", e.localizedMessage)
        }
    }
}
