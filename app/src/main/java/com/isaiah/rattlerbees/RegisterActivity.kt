package com.isaiah.rattlerbees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "RegisterActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val etEmail = findViewById<EditText>(R.id.register_etEmail)
        val etName = findViewById<EditText>(R.id.register_etName)
        val etPassword = findViewById<EditText>(R.id.register_etPassword)
        val btnSubmit = findViewById<Button>(R.id.register_btnSubmit)
        val btnCancel = findViewById<Button>(R.id.register_btnCancel)

        btnSubmit.setOnClickListener{
            // Perform this code when button is pressed

            if(etEmail.text.trim().toString().isNotEmpty() && etPassword.text.trim().toString().isNotEmpty() && etName.text.trim().toString().isNotEmpty()) {

                val email = etEmail.text.trim().toString()
                val password = etPassword.text.trim().toString()

                registerNewUser(email, password)

            } else {

                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()

            }







        }

        btnCancel.setOnClickListener {
            // Perform this code when button is pressed

            goToLoginActivity()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            goToMainActivity();
        }
    }


    private fun goToMainActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
//        finish()
    }

    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
//        finish()
    }

    private fun registerNewUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // Sign in success, update UI with current user's information
                        Log.d(TAG, "createUserWithEmail: Success")
                        goToMainActivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

}