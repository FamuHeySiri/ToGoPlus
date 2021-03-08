package com.isaiah.rattlerbees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val btnLogout = findViewById<Button>(R.id.main_btnLogout)

        btnLogout.setOnClickListener {
            signOut()
        }
    }


    private fun signOut(){
        Firebase.auth.signOut()
        goToLoginActivity()
    }

    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
//        finish()
    }
}