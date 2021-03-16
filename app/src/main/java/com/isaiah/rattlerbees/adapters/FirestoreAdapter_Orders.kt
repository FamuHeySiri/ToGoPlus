package com.isaiah.rattlerbees.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.OrdersModel
import com.isaiah.rattlerbees.models.UserModel
import com.isaiah.rattlerbees.utilities.OrdersViewHolder
import com.isaiah.rattlerbees.utilities.UserViewHolder
import java.util.*

class FirestoreAdapter_Orders(options: FirestoreRecyclerOptions<OrdersModel>) : FirestoreRecyclerAdapter<OrdersModel, OrdersViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_order_card, parent, false)
        return OrdersViewHolder(view)    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int, model: OrdersModel) {

        val order_id:  TextView = holder.itemView.findViewById(R.id.orders_card_order_id)
        val user_id: TextView = holder.itemView.findViewById(R.id.orders_card_customer_name)
        val order_time: TextView = holder.itemView.findViewById(R.id.orders_card_order_time)
        val order_status: TextView = holder.itemView.findViewById(R.id.orders_card_order_status)

        order_id.text = model.order_id
        user_id.text = model.user_id
        order_time.text = model.order_time.toString()
        order_status.text = model.order_status
    }

}
