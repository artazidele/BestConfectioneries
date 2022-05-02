package com.example.bestconfectioneries.drinks.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DrinkDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getDrinks(): Task<QuerySnapshot> {
        return db.collection("Drink")
            .orderBy("name")
            .get()
    }

    suspend fun getOneConfectioneryDrinks(confectioneryId: String): Task<QuerySnapshot> {
        return db.collection("Drink")
            .whereEqualTo("confectioneryId", confectioneryId)
            .orderBy("name")
            .get()
    }

    suspend fun deleteDrink(id: String): Task<Void> {
        return db.collection("Drink").document(id)
            .delete()
    }

    suspend fun getDrink(id: String): Task<DocumentSnapshot> {
        return db.collection("Drink")
            .document(id)
            .get()
    }

    suspend fun addDrink(drink: Drink): Task<Void> {
        return db.collection("Drink").document(drink.id)
            .set(drink)
    }

    suspend fun updateDrink(drink: Drink): Task<Void> {
        return db.collection("Drink").document(drink.id)
            .update(
                mapOf(
                    "name" to drink.name,
                    "tea" to drink.tea,
                    "centi" to drink.centi,
                    "eiro" to drink.eiro,
                    "capacity" to drink.capacity,
                    "coffee" to drink.coffee,
                    "other" to drink.other,
                    "description" to drink.description,
                    "editedBy" to drink.editedBy,
                    "editedOn" to drink.editedOn
                )
            )
    }
}