package com.example.bestconfectioneries.items.viewmodel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestconfectioneries.items.model.ImageStorage
import com.example.bestconfectioneries.items.model.Item
import com.example.bestconfectioneries.items.model.ItemDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

enum class ItemNetworkStatus { LOADING, ERROR, DONE }

class ItemViewModel : ViewModel() {
    private val _status = MutableLiveData<ItemNetworkStatus>()
    val status: LiveData<ItemNetworkStatus> = _status

    fun addNewItem(item: Item, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            ItemDatabase().addItem(item)
                .addOnSuccessListener { documents ->
                    _status.value = ItemNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun getOneItem(id: String, onResult: (Item?) -> Unit) {
        viewModelScope.launch {
            _status.value = ItemNetworkStatus.LOADING
            ItemDatabase().getItem(id)
                .addOnSuccessListener { document ->
                    val item = document.toObject<Item>()
                    if (item?.id != null) {
                        _status.value = ItemNetworkStatus.DONE
                        onResult(item)
                    } else {
                        _status.value = ItemNetworkStatus.ERROR
                        onResult(null)
                    }

                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun deleteOneItem(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = ItemNetworkStatus.LOADING
            ItemDatabase().deleteItem(id)
                .addOnSuccessListener {
                    _status.value = ItemNetworkStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun updateOneItem(item: Item, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = ItemNetworkStatus.LOADING
            ItemDatabase().updateItem(item)
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.DONE
                    onResult(false)
                }
        }
    }

    fun getAllItems(onResult: (ArrayList<Item>?) -> Unit) {
        viewModelScope.launch {
            var itemList = ArrayList<Item>()
            _status.value = ItemNetworkStatus.LOADING
            ItemDatabase().getItems()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val item = document.toObject<Item>()
                        itemList.add(item)
                    }
                    _status.value = ItemNetworkStatus.DONE
                    onResult(itemList)
                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun getThisConfectioneryItems(confectioneryId: String, onResult: (ArrayList<Item>?) -> Unit) {
        viewModelScope.launch {
            var itemList = ArrayList<Item>()
            _status.value = ItemNetworkStatus.LOADING
            ItemDatabase().getOneConfectioneryItems(confectioneryId)
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val item = document.toObject<Item>()
                        itemList.add(item)
                    }
                    _status.value = ItemNetworkStatus.DONE
                    onResult(itemList)
                }
                .addOnFailureListener {
                    _status.value = ItemNetworkStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun addImage(id: String, imageView: ImageView, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = ItemNetworkStatus.LOADING
           ImageStorage().saveImage(id, imageView)
                .addOnSuccessListener {
                    onResult(true)
                }
                .addOnFailureListener {
                    onResult(false)
                }
        }
    }
}
