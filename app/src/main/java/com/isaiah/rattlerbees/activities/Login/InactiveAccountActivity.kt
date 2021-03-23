package com.isaiah.rattlerbees.activities.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R

class InactiveAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inactive_account)
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
}