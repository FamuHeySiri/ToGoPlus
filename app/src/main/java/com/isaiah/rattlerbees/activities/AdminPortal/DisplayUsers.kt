package com.isaiah.rattlerbees.activities.AdminPortal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.adapters.FirestoreAdapter
import com.isaiah.rattlerbees.models.UserModel
import kotlin.math.sign


class DisplayUsers : AppCompatActivity() {

    private companion object {
        private const val TAG = "DISPLAY_USERS"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val user_query = db.collection("USERS")
    val options = FirestoreRecyclerOptions.Builder<UserModel>().setQuery(user_query, UserModel::class.java)
        .setLifecycleOwner(this).build()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // inflate menu
        menuInflater.inflate(R.menu.options_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.menu_search){
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
        }
        if (id == R.id.option_logout){
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show()
            signOut()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_users)

        val recyclerView = findViewById<RecyclerView>(R.id.viewUsers_recyclerView)


        // create instance for current auth token
        auth = Firebase.auth

        // assign layoutmanager and adapter to the view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirestoreAdapter(options)
    }


    // sign out current user
    private fun signOut(){
        Firebase.auth.signOut()
        goToLoginActivity()
    }


    // navigate to login activity
    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}