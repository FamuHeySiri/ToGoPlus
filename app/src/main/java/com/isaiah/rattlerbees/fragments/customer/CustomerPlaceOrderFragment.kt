package com.isaiah.rattlerbees.fragments.customer

import android.os.Bundle
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.ProductModel
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat

class CustomerPlaceOrderFragment : Fragment() {

    private companion object {
        private const val TAG = "CUST_MENU"
    }

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // query menu items
    val menu_query = db
            .collection("MENU_ITEMS")
            .orderBy("item_category", Query.Direction.DESCENDING)
    val options = FirestoreRecyclerOptions.Builder<ProductModel>().setQuery(menu_query, ProductModel::class.java)
            .setLifecycleOwner(this).build()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_menu_items, container, false)




        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerView_productMenu)

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerView != null) {
            recyclerView.adapter = MenuAdapter(options)
        }
    }


    inner class MenuAdapter(options: FirestoreRecyclerOptions<ProductModel>) : FirestoreRecyclerAdapter<ProductModel, MenuViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_menu_item, parent, false)
            return MenuViewHolder(view)
        }

        override fun onBindViewHolder(holder: MenuViewHolder, position: Int, model: ProductModel) {
            val product_name: TextView = holder.itemView.findViewById(R.id.textView_productName)
            val product_price: TextView = holder.itemView.findViewById(R.id.textView_productPrice)
            val product_image: ImageView = holder.itemView.findViewById(R.id.image_menuItem)

            val btnAddToCart: Button = holder.itemView.findViewById(R.id.button_addToCart)

            // Get currently selected items ID
            val item_uid = options.snapshots.get(position).item_id

            val priceFormat = DecimalFormat("#.##")
            priceFormat.roundingMode = RoundingMode.CEILING

            val price = model.item_price

            val priceString = "$%.2f".format(price)

            product_name.text = model.item_name
            product_price.text = priceString

            if (model.item_image.isEmpty()) {
                product_image.setImageResource(R.mipmap.menu_sample_item)
            } else {
                Picasso.get().load(model.item_image).into(product_image)
            }

            btnAddToCart.setOnClickListener {
                addToCartList(item_uid);

            }

        }

        private fun addToCartList(item_uid: String) {

            // create instance for current auth token
            auth = Firebase.auth

            db.collection("USERS")
                .document(auth.currentUser.uid)
                .update("shopping_cart", FieldValue.arrayUnion("/MENU_ITEMS/$item_uid/"))

        }


    }

    inner class MenuViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }


}