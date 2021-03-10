package com.isaiah.rattlerbees.activities.CustomerPortal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.activities.Login.RegisterActivity

class UserPortal : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_portal)

        // create instance for current auth token
        auth = Firebase.auth

        // instantiate layout objects
        val btnLogout = findViewById<Button>(R.id.userPortal_btnLogout)

        // log the current users information
        readUserDoc()

        btnLogout.setOnClickListener {
            signOut()
        }

        // TODO: 3/9/21 Deactivate account
        // TODO: 3/9/21 Start an Order
        // TODO: 3/9/21 Show Menu
        // TODO: 3/9/21 Select category
        // TODO: 3/9/21 Add an item to order
        // TODO: 3/9/21 Show current order
        // TODO: 3/9/21 Place order
        // TODO: 3/9/21 Show status
        // TODO: 3/9/21 Cancel order 
        // TODO: 3/9/21 Rate an order 
        // TODO: 3/9/21 View order history 
        
    }

    private fun signOut(){
        Firebase.auth.signOut()
        goToLoginActivity()
    }

    private fun readUserDoc(){
        // read single document from user collection
        db.collection("USERS").document(auth.currentUser.uid)
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

    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}