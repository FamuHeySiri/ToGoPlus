package com.isaiah.rattlerbees.fragments.customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.ProductModel
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat


class CustomerShoppingCartFragment : Fragment() {

    private companion object {
        private const val TAG = "SHOPPING_CART"
    }

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // query menu items
//    val cart_query = db.collection("USERS")
//            .whereEqualTo("user_uid", auth.currentUser.uid)


//    val options = FirestoreRecyclerOptions.Builder<ProductModel>().setQuery(cart_query, ProductModel::class.java)
//            .setLifecycleOwner(this).build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_shopping_cart, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_userCart)

        // assign layout manager and adapter to the view
//        if (recyclerView != null) {
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = CartAdapter(options)
//        }


    }

//    inner class CartAdapter(options: FirestoreRecyclerOptions<ProductModel>) : FirestoreRecyclerAdapter<ProductModel, CartViewHolder>(options) {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerShoppingCartFragment.CartViewHolder {
//            // inflate individual row layout
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_menu_item, parent, false)
//            return CartViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: CartViewHolder, position: Int, model: ProductModel) {
//            val product_name: TextView = holder.itemView.findViewById(R.id.textView_cartItemName)
//            val product_quantity: TextView = holder.itemView.findViewById(R.id.textView_cartItemQuantity)
//            val product_price: TextView = holder.itemView.findViewById(R.id.textView_cartItemPrice)
//
//            val priceFormat = DecimalFormat("#.##")
//            priceFormat.roundingMode = RoundingMode.CEILING
//
//            val price = model.item_price
//            val priceString = "$%.2f".format(price)
//
//            product_name.text = model.item_name
//            product_price.text = priceString
//            product_quantity.text = "x1"
//
//        }
//
//    }
//
//    inner class CartViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
//
//    }
}


// create reference to current users shopping cart
// for each item in the cart
// gather name, price
// write item name to receipt create new line
// add price to running subtotal
// write total

