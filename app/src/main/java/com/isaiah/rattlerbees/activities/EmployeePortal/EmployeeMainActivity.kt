package com.isaiah.rattlerbees.activities.EmployeePortal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.activities.Login.RegisterActivity
import com.isaiah.rattlerbees.fragments.admin.ViewUserDetailFragment
import com.isaiah.rattlerbees.fragments.employee.EmployeeCompletedOrdersFragment
import com.isaiah.rattlerbees.fragments.employee.EmployeeEditOrderFragment
import com.isaiah.rattlerbees.fragments.employee.EmployeeHomeFragment
import com.isaiah.rattlerbees.fragments.employee.EmployeeViewOrdersFragment
import com.isaiah.rattlerbees.utilities.Communicator
import com.isaiah.rattlerbees.utilities.FirestoreQueries

class EmployeeMainActivity : AppCompatActivity(), Communicator {

    private companion object {
        const val TAG = "EMPLOYEE_MAIN_ACTIVITY"
    }

    // TODO: 3/20/21 remove this code to test using external classes
    val firebaseUtil = FirestoreQueries()

    // declare instances of fragments
    val employeeHomeFragment = EmployeeHomeFragment()
    val employeeViewOrdersFragment = EmployeeViewOrdersFragment()
    val employeeCompletedOrdersFragment = EmployeeCompletedOrdersFragment()

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_main)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView_Employee)

        setCurrentFragment(employeeHomeFragment)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.employeeHomeFragment -> {
                    setCurrentFragment(employeeHomeFragment)
                }
                R.id.employeeViewOrdersFragment -> {
                    setCurrentFragment(employeeViewOrdersFragment)
                }
                R.id.employeeCompletedOrdersFragment -> {
                    setCurrentFragment(employeeCompletedOrdersFragment)
                }
            }
        }



        // create instance for current auth token
        auth = Firebase.auth

        // TODO: 3/9/21 show orders
        // TODO: 3/9/21 change order status
        // TODO: 3/9/21 show order rating
    }

    // swap out and replace current fragment
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_Employee, fragment)
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

    override fun passDataCom(inputValue: String) {
        TODO("Not yet implemented")
    }

    override fun passDataComEditOrder(inputValue: String) {

        val bundle = Bundle()
        bundle.putString("order_id", inputValue)

        val transaction = this.supportFragmentManager.beginTransaction()
        val EditOrderFragment = EmployeeEditOrderFragment()
        EditOrderFragment.arguments = bundle

        transaction.replace(R.id.frameLayout_Employee, EditOrderFragment)
        transaction.commit()

    }
}