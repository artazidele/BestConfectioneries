package com.example.bestconfectioneries.items.model

import com.example.bestconfectioneries.drinks.model.Drink
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ItemDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getItems(): Task<QuerySnapshot> {
        return db.collection("Item")
            .orderBy("name")
            .get()
    }

    suspend fun getOneConfectioneryItems(confectioneryId: String): Task<QuerySnapshot> {
        return db.collection("Item")
            .whereEqualTo("confectioneryId", confectioneryId)
            .orderBy("name")
            .get()
    }

    suspend fun deleteItem(id: String): Task<Void> {
        return db.collection("Item").document(id)
            .delete()
    }

    suspend fun getItem(id: String): Task<DocumentSnapshot> {
        return db.collection("Item")
            .document(id)
            .get()
    }

    suspend fun addItem(item: Item): Task<Void> {
        return db.collection("Item").document(item.id)
            .set(item)
    }

    suspend fun updateItem(item: Item): Task<Void> {
        return db.collection("Item").document(item.id)
            .update(
                mapOf(
                    "name" to item.name,
                    "cake" to item.cake,
                    "bun" to item.bun,
                    "cookies" to item.cookies,
                    "weight" to item.weight,
                    "eiro" to item.eiro,
                    "centi" to item.centi,
                    "description" to item.description,
                    "editedBy" to item.editedBy,
                    "editedOn" to item.editedOn,
                    "forVegetarians" to item.forVegetarians,
                    "forVegans" to item.forVegans,
                    "allergens" to item.allergens
                )
            )
    }
}
