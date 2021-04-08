package com.isaiah.rattlerbees.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R


class CustomerShoppingCartFragment : Fragment() {

    private companion object {
        private const val TAG = "SHOPPING_CART"
    }

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_shopping_cart, container, false)
    }

}


// create reference to current users shopping cart
// for each item in the cart
    // gather name, price
    // write item name to receipt create new line
    // add price to running subtotal
// write total

