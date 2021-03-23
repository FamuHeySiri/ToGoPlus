package com.isaiah.rattlerbees.utilities

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.activities.Login.RegisterActivity

class FirestoreQueries {

    // Initialize Firebase Auth
    var auth: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    public val orders_query = db.collection("ORDERS").whereEqualTo("user_id", auth?.uid.toString())

    // Read Specific user document
    fun readUserDoc(userId: String){
        // read single document from user collection
        db.collection("USERS").document(userId)
            .get()
            .addOnSuccessListener {document ->
                if (document != null){
                    Log.d(RegisterActivity.TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }

    // Read Specific user document
    fun readAllUsers(){
        // read single document from user collection
        db.collection("USERS")
            .get()
            .addOnSuccessListener {document ->
                if (document != null){
                    Log.d(RegisterActivity.TAG, "DocumentSnapshot data: ${document}")
                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }

    // Read all order document
    fun readAllOrders(){
        // read single document from user collection
        db.collection("ORDERS")
            .get()
            .addOnSuccessListener {document ->
                if (document != null){
                    Log.d(RegisterActivity.TAG, "DocumentSnapshot data: ${document}")
                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }


    // Read Specific user order history
    fun readOrderHistory(currentUserId: String){
        // read single document from user collection
        db.collection("ORDERS")
            .whereEqualTo("user_id", currentUserId)
            .get()
            .addOnSuccessListener {document ->
                if (document != null){
                    Log.d(RegisterActivity.TAG, "DocumentSnapshot data: ${document}")
                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }

    fun fsTest(Tag: String, Msg: String){
        Log.d(Tag, Msg)
    }


}