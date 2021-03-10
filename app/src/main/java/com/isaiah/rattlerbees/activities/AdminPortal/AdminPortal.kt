package com.isaiah.rattlerbees.activities.AdminPortal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity


class AdminPortal : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_portal)

        // create instance for current auth token
        auth = Firebase.auth

        // instantiate layout objects
        val btnLogout = findViewById<Button>(R.id.adminPortal_btnLogout)

        btnLogout.setOnClickListener {
            signOut()
        }

        // TODO: 3/9/21 Search for specific users
        // TODO: 3/9/21 Deactivate a user / ban a user
        // TODO: 3/9/21 Show orders by date range
        // TODO: 3/9/21 show orders by category
        // TODO: 3/9/21 show orders by customer

    }

    private fun signOut(){
        Firebase.auth.signOut()
        goToLoginActivity()
    }

    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}