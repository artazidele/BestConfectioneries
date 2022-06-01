package com.example.bestconfectioneries.confectioneries.model

data class Confectionery(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val email: String = "",
    val phone: String = "",
    val ownerId: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList(),
    val photosId: ArrayList<String> = ArrayList()
)
