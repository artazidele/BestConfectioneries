package com.example.bestconfectioneries.drinks.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DrinkDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getDrinks(): Task<QuerySnapshot> {
        return db.collection("Drink")
            .orderBy("title")
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

    suspend fun updateAllergen(drink: Drink): Task<Void> {
        return db.collection("Drink").document(drink.id)
            .update(
                mapOf(
                    "title" to drink.title,
                    "tea" to drink.tea,
                    "coffee" to drink.coffee,
                    "other" to drink.other,
                    "description" to drink.description,
                    "editedBy" to drink.editedBy,
                    "editedOn" to drink.editedOn
                )
            )
    }
}