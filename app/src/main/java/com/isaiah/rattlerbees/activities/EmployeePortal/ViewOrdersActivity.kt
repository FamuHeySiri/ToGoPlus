package com.isaiah.rattlerbees.activities.EmployeePortal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.adapters.FirestoreAdapter_Orders
import com.isaiah.rattlerbees.adapters.FirestoreAdapter_Users
import com.isaiah.rattlerbees.models.OrdersModel
import com.isaiah.rattlerbees.models.UserModel

class ViewOrdersActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "VIEW_ORDERS_ACTIVITY"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val orders_query = db.collection("ORDERS")
    val options = FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(orders_query, OrdersModel::class.java)
            .setLifecycleOwner(this).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_orders)

        val recyclerView = findViewById<RecyclerView>(R.id.viewOrders_recyclerView)


        // create instance for current auth token
        auth = Firebase.auth

        // assign layoutmanager and adapter to the view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirestoreAdapter_Orders(options)
    }


    // top bar navigation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // inflate menu
        menuInflater.inflate(R.menu.options_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }


    // top menu item actions
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


    // sign out current user
    private fun signOut(){
        Firebase.auth.signOut()
        goToLoginActivity()
        finish()
    }


    // navigate to login activity
    private fun goToLoginActivity() {
        // create intent to navigate to main activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}