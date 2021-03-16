package com.isaiah.rattlerbees.activities.CustomerPortal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.activities.Login.RegisterActivity
import com.isaiah.rattlerbees.fragments.customer.*

class CustomerMainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // declare instances of fragments
    val customerHomeFragment = CustomerHomeFragment()
    val customerLocationsFragment = CustomerLocationsFragment()
    val customerOrderHistoryFragment = CustomerOrderHistoryFragment()
    val customerPlaceOrderFragment = CustomerPlaceOrderFragment()
    val customerShoppingCartFragment = CustomerShoppingCartFragment()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_main)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView_Customer)

        setCurrentFragment(customerHomeFragment)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.customerHomeFragment -> {
                    setCurrentFragment(customerHomeFragment)
                }
                R.id.customerLocationsFragment -> {
                    setCurrentFragment(customerLocationsFragment)
                }
                R.id.customerOrderHistoryFragment -> {
                    setCurrentFragment(customerOrderHistoryFragment)
                }
                R.id.customerPlaceOrderFragment -> {
                    setCurrentFragment(customerPlaceOrderFragment)
                }
                R.id.customerShoppingCartFragment -> {
                    setCurrentFragment(customerShoppingCartFragment)
                }
            }
        }

        // create instance for current auth token
        auth = Firebase.auth


        // log the current users information
        readUserDoc()


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

    // swap out and replace current fragment
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_Customer, fragment)
            commit()
        }
    }

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