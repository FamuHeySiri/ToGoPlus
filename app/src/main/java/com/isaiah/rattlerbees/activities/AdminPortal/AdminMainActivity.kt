package com.isaiah.rattlerbees.activities.AdminPortal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.activities.Login.LoginActivity
import com.isaiah.rattlerbees.fragments.admin.AdminHomeFragment
import com.isaiah.rattlerbees.fragments.admin.AdminViewOrdersFragment
import com.isaiah.rattlerbees.fragments.admin.AdminViewUsersFragment
import com.isaiah.rattlerbees.fragments.admin.ViewUserDetailFragment
import com.isaiah.rattlerbees.models.UserModel
import com.isaiah.rattlerbees.utilities.Communicator


class AdminMainActivity : AppCompatActivity(), Communicator {

    private companion object {
        private const val TAG = "ADMIN_MAIN_ACTIVITY"
    }

    // declare instance of fragments
    val adminHomeFragment = AdminHomeFragment()
    val adminViewUsersFragment = AdminViewUsersFragment()
    val adminViewOrdersFragment = AdminViewOrdersFragment()

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView_Admin)

        setCurrentFragment(adminHomeFragment)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.adminHomeFragment -> {
                    setCurrentFragment(adminHomeFragment)
                }
                R.id.adminViewOrdersFragment -> {
                    setCurrentFragment(adminViewOrdersFragment)
                }
                R.id.adminUsersFragment -> {
                    setCurrentFragment(adminViewUsersFragment)
                }
            }
        }
    }

    // swap out and replace current fragment
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_Admin, fragment)
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

    override fun passDataCom(inputValue: String) {

        val bundle = Bundle()
        bundle.putString("user_uid", inputValue)

        val transaction = this.supportFragmentManager.beginTransaction()
        val userDetailFragment = ViewUserDetailFragment()
        userDetailFragment.arguments = bundle

        transaction.replace(R.id.frameLayout_Admin, userDetailFragment)
        transaction.commit()

    }
}