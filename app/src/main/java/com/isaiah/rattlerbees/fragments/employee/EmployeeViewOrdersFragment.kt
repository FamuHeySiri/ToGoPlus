package com.isaiah.rattlerbees.fragments.employee

import android.os.Bundle
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.OrdersModel

class EmployeeViewOrdersFragment : Fragment() {

    private companion object {
        private const val TAG = "EMPLOYEE_VIEW_ORDERS_FRAGMENT"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val orders_query = db.collection("ORDERS").orderBy("order_time")
    val options = FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(orders_query, OrdersModel::class.java)
        .setLifecycleOwner(this).build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_view_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_employee_view_orders)

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerView != null) {
            recyclerView.adapter = EmployeeViewOrdersAdapter(options)
        }

    }

    inner class EmployeeViewOrdersAdapter(options: FirestoreRecyclerOptions<OrdersModel>) : FirestoreRecyclerAdapter<OrdersModel, EmployeeViewOrdersFragment.EmployeeOrdersViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewOrdersFragment.EmployeeOrdersViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_order_card, parent, false)
            return EmployeeOrdersViewHolder(view)
        }

        override fun onBindViewHolder(holder: EmployeeViewOrdersFragment.EmployeeOrdersViewHolder, position: Int, model: OrdersModel) {

            val order_id: TextView = holder.itemView.findViewById(R.id.orders_card_order_id)
            val user_id: TextView = holder.itemView.findViewById(R.id.orders_card_customer_name)
            val order_time: TextView = holder.itemView.findViewById(R.id.orders_card_order_time)
            val order_status: TextView = holder.itemView.findViewById(R.id.orders_card_order_status)

            order_id.text = model.order_id
            user_id.text = model.user_id
            order_time.text = model.order_time.toString()
            order_status.text = model.order_status

        }


    }


    inner class EmployeeOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}