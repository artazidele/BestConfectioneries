package com.example.bestconfectioneries.items.model

data class Item(
    val id: String = "",
    val confectioneryId: String = "",
    val name: String = "",
    val cake: Boolean = false,
    val bun: Boolean = false,
    val cookies: Boolean = false,
    val eiro: Int = 0,
    val centi: Int = 0,
    val weight: Int = 0,
    val description: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList(),
    val forVegetarians: Boolean = false,
    val forVegans: Boolean = false,
    val allergens: String = ""
)
