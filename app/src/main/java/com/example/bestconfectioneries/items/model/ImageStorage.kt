package com.example.bestconfectioneries.items.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ImageStorage {
    val storage = Firebase.storage

    @RequiresApi(Build.VERSION_CODES.O)
    public fun saveImage(itemId: String, productImageView: ImageView): UploadTask {
        val storageRef = storage.reference
        val productRef = storageRef.child(itemId)
        val productImageView = productImageView
        productImageView.isDrawingCacheEnabled = true
        productImageView.buildDrawingCache()
        val bitmap = (productImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        return productRef.putBytes(data)
    }

    public fun deleteImage(productId: String): Task<Void> {
        val storageRef = storage.reference
        val imageToDeleteRef = storageRef.child(productId)
        return imageToDeleteRef.delete()
    }

    public fun getImageReference(id: String): StorageReference {
        val imageReference = storage.reference.child(id)
        return imageReference
    }
}