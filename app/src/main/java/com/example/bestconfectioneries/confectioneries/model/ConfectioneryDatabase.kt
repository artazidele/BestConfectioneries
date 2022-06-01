package com.example.bestconfectioneries.confectioneries.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ConfectioneryDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getConfectioneries(): Task<QuerySnapshot> {
        return db.collection("Confectionery")
            .orderBy("name")
            .get()
    }

    suspend fun deleteConfectionery(id: String): Task<Void> {
        return db.collection("Confectionery").document(id)
            .delete()
    }

    suspend fun getConfectionery(id: String): Task<DocumentSnapshot> {
        return db.collection("Confectionery")
            .document(id)
            .get()
    }

    suspend fun addConfectionery(confectionery: Confectionery): Task<Void> {
        return db.collection("Confectionery").document(confectionery.id)
            .set(confectionery)
    }

    suspend fun updateConfectionery(confectionery: Confectionery): Task<Void> {
        return db.collection("Confectionery").document(confectionery.id)
            .update(
                mapOf(
                    "name" to confectionery.name,
                    "description" to confectionery.description,
                    "address" to confectionery.address,
                    "email" to confectionery.email,
                    "phone" to confectionery.phone,
                    "editedBy" to confectionery.editedBy,
                    "editedOn" to confectionery.editedOn,
                    "photosId" to confectionery.photosId
                )
            )
    }
}