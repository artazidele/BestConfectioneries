package com.example.bestconfectioneries.drinks.model

data class Drink(
    val id: String = "",
    val confectioneryId: String = "",
    val name: String = "",
    val coffee: Boolean = false,
    val tea: Boolean = false,
    val other: Boolean = false,
    val eiro: Int = 0,
    val centi: Int = 0,
    val capacity: Int = 0,
    val description: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList()
)
