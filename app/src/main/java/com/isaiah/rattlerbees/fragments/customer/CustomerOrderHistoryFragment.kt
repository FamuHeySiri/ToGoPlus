package com.isaiah.rattlerbees.fragments.customer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.OrdersModel


class CustomerOrderHistoryFragment : Fragment() {

    private companion object {
        private const val TAG = "CUSTOMER_ORDER_HISTORY_FRAGMENT"
    }

//    // Initialize Firebase Auth
    var auth: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val orders_query = db
        .collection("ORDERS")
        .whereEqualTo("user_id", auth?.uid.toString())
        .orderBy("order_time", Query.Direction.DESCENDING)
        .limit(50)
    val options = FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(orders_query, OrdersModel::class.java)
        .setLifecycleOwner(this).build()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_order_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // create instance for current auth token
//        auth = Firebase.auth

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_customer_view_order_history)

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        if (recyclerView != null) {
            recyclerView.adapter = CustomerOrderHistoryAdapter(options)
        }

    }

    inner class CustomerOrderHistoryAdapter(options: FirestoreRecyclerOptions<OrdersModel>) : FirestoreRecyclerAdapter<OrdersModel, CustomerOrderHistoryFragment.CustomerOrderHistoryViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerOrderHistoryFragment.CustomerOrderHistoryViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_order, parent, false)
            return CustomerOrderHistoryViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: CustomerOrderHistoryFragment.CustomerOrderHistoryViewHolder, position: Int, model: OrdersModel) {

            val order_id: TextView = holder.itemView.findViewById(R.id.orders_card_order_id)
            val order_time: TextView = holder.itemView.findViewById(R.id.orders_card_order_time)
            val order_status: TextView = holder.itemView.findViewById(R.id.orders_card_order_status)
            val user_name: TextView = holder.itemView.findViewById(R.id.orders_card_customer_name)

            val userRef = db.collection("USERS").document(model.user_id.toString())
            userRef.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("EMP_V.O.F", "DocumentSnapshot data: ${document.data}")
                        user_name.text = document.getString("user_firstName").toString() + " " + document.getString("user_lastName").toString()

                    } else {
                        Log.d("EMP_V.O.F", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("EMP_V.O.F", "get failed with ", exception)
                }

            order_id.text = model.order_id
            order_time.text = model.order_time.toString()
            order_status.text = model.order_status

        }


    }

    inner class CustomerOrderHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}