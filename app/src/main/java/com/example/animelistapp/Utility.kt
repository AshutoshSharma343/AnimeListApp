package com.example.animelistapp

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Utility {
    val collectionReferenceForNotes: CollectionReference
        get() {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return FirebaseFirestore.getInstance().collection("notes").document(currentUser!!.uid)
                .collection("myNotes")
        }
}