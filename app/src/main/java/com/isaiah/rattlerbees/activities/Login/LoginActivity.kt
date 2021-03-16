package com.isaiah.rattlerbees.activities.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.*
import com.isaiah.rattlerbees.activities.AdminPortal.AdminMainActivity
import com.isaiah.rattlerbees.activities.AdminPortal.DisplayUsers
import com.isaiah.rattlerbees.activities.CustomerPortal.CustomerMainActivity
import com.isaiah.rattlerbees.activities.EmployeePortal.EmployeeMainActivity
import com.isaiah.rattlerbees.activities.EmployeePortal.ViewOrdersActivity

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val etEmail = findViewById<EditText>(R.id.login_etEmail)
        val etPassword = findViewById<EditText>(R.id.login_etPassword)
        val btnLogin = findViewById<Button>(R.id.login_btnLogin)
        val btnRegister = findViewById<Button>(R.id.login_btnRegister)

        btnLogin.setOnClickListener{
            // Perform this code when button is pressed
            if(etEmail.text.trim().toString().isNotEmpty() || etPassword.text.trim().toString().isNotEmpty()){

                val email = etEmail.text.trim().toString()
                val password = etPassword.text.trim().toString()

                signOn(email, password)

            } else {

                Toast.makeText(this, "Input required", Toast.LENGTH_LONG).show()

            }
        }

        btnRegister.setOnClickListener {
            // Perform this code when button is pressed

            // create intent to navigate to registration activity
            val intent = Intent(this, RegisterActivity::class.java)
            // start next activity
            startActivity(intent)
        }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            navigateToPortal();
        }
    }

    private fun goToDisplayUsersActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, DisplayUsers::class.java)
        startActivity(intent)
        finish()
    }


    private fun goToViewOrdersActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, ViewOrdersActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToAdminMainActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, AdminMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToEmployeeMainActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, EmployeeMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToCustomerMainActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, CustomerMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToPortal(){
        // read single document from user collection
        db.collection("USERS").document(auth.currentUser.uid)
            .get()
            .addOnSuccessListener {document ->
                if (document != null){
                    Log.d(RegisterActivity.TAG, "DocumentSnapshot data: ${document.data}")

                    val accountType = document.getString("user_accountType")

                    if (accountType == "ADMIN"){
                        // go to admin portal
                        goToAdminMainActivity()
                    } else if (accountType == "EMPLOYEE") {
                        // go to employee portal
                        goToEmployeeMainActivity()
                    } else if (accountType == "CUSTOMER") {
                        // go to user portal
                        goToCustomerMainActivity()
                    } else {
                        Log.d(TAG, "user account type not found")
                    }

                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }


    private fun signOn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // Sign in success, update UI with current user's information
                        Log.d(RegisterActivity.TAG, "signInWithEmail: Success")
                        navigateToPortal()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(RegisterActivity.TAG, "signInWithEmail: Failure", task.exception)
                        Toast.makeText(baseContext, "Incorrect username or password",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }



}
