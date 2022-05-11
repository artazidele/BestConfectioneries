package com.example.bestconfectioneries.users.model

data class Confectioner(
    val userId: String = "",
    val confectioneriesId: ArrayList<String> = ArrayList(),
    val name: String = "",
    val surname: String = "",
    val phone: String = ""
)
