package com.example.bestconfectioneries.drinks.model

data class Drink(
    val id: String = "",
    val title: String = "",
    val coffee: Boolean = false,
    val tea: Boolean = false,
    val other: Boolean = false,
    val description: String = "",
    val madeBy: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList()
)
