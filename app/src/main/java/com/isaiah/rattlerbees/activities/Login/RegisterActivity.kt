package com.isaiah.rattlerbees.activities.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.AdminPortal.AdminMainActivity
import com.isaiah.rattlerbees.activities.AdminPortal.DisplayUsers
import com.isaiah.rattlerbees.activities.CustomerPortal.CustomerMainActivity
import com.isaiah.rattlerbees.activities.EmployeePortal.EmployeeMainActivity


class RegisterActivity : AppCompatActivity() {

    // companion object used to create a constant value
    companion object {
        const val TAG = "RegisterActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // create instance of firebase auth
        auth = Firebase.auth

        // declare activity objects and fields
        val etEmail = findViewById<EditText>(R.id.register_etEmail)
        val etName = findViewById<EditText>(R.id.register_etName)
        val etPassword = findViewById<EditText>(R.id.register_etPassword)
        val btnSubmit = findViewById<Button>(R.id.register_btnSubmit)
        val btnCancel = findViewById<Button>(R.id.register_btnCancel)

        // Perform this code when button is pressed
        btnSubmit.setOnClickListener{

            // if all fields are completed
            if(etEmail.text.trim().toString().isNotEmpty() && etPassword.text.trim().toString().isNotEmpty() && etName.text.trim().toString().isNotEmpty()) {


                // TODO: 3/15/21 Replace with specific error notifications 
                // set values
                val email = etEmail.text.trim().toString()
                val password = etPassword.text.trim().toString()
                val name = etName.text.trim().toString()

                // register new user to system
                registerNewUser(email, password, name)

            } else {

                // notify user of empty fields
                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()
            }
            
            
            
            
        }

        // Perform this code when button is pressed
        btnCancel.setOnClickListener {

            // navigate to login screen
            goToLoginActivity()
        }
    }

    // stores new user data into firestore
    private fun addDataToFirestore(email: String, name: String) {

        // convert full name to an array of names
        val nameArray = name.split(" ").toTypedArray()

        // Declare hashmap for the users data
        val userHashMap = HashMap<String, Any>()

        // Set user data to hashmap
        userHashMap["user_uid"] = auth.currentUser.uid
        userHashMap["user_firstName"] = nameArray[0]
        userHashMap["user_lastName"] = nameArray[1]
        userHashMap["user_email"] = email
        userHashMap["user_phoneNumber"] = ""
        userHashMap["user_accountType"] = "CUSTOMER"


        // To create or overwrite a specific document using the set method
        db.collection("USERS").document(auth.currentUser.uid)
                .set(userHashMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${auth.currentUser.uid}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

    }

    // read data from current user
    fun readUserCollection(){
        db.collection("USERS")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }


    // on activity startup
    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            navigateToPortal();
        }
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
                        goToDisplayUsersActivity()
                    } else if (accountType == "EMPLOYEE") {
                        // go to employee portal
                        goToEmployeePortal()
                    } else if (accountType == "CUSTOMER") {
                        // go to user portal
                        goToUserPortal()
                    } else {
                        Log.d(LoginActivity.TAG, "user account type not found")
                    }


                } else {
                    Log.d(RegisterActivity.TAG, "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(RegisterActivity.TAG, "get() failed with ", exception)
            }
    }

    private fun goToDisplayUsersActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, DisplayUsers::class.java)
        startActivity(intent)
        finish()
    }


    private fun goToAdminPortal() {
        // create intent to navigate to main activity
        val intent = Intent(this, AdminMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToEmployeePortal() {
        // create intent to navigate to main activity
        val intent = Intent(this, EmployeeMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToUserPortal() {
        // create intent to navigate to main activity
        val intent = Intent(this, CustomerMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // create intent to navigate to log in activity
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // register new user account with email
    private fun registerNewUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // Sign in success, update UI with current user's information
                        Log.d(TAG, "createUserWithEmail: Success")
                        addDataToFirestore(email, name)
                        navigateToPortal()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

}