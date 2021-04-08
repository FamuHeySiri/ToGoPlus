package com.isaiah.rattlerbees.fragments.employee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.isaiah.rattlerbees.R
import java.time.Instant

class EmployeeEditOrderFragment : Fragment() {

    // Identification tag for fragment
    private companion object {
        private const val TAG = "EDIT_ORDER_FRAGMENT"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Stores the user_uid passed from bundle
    var selectedOrderId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employee_edit_order, container, false)

        // Get order from the passed fragments arguments
        selectedOrderId = arguments?.getString("order_id")

        // create an instance of the fragment objects
        val order_title: TextView = view.findViewById(R.id.textView_EditOrder_preparingOrder)
        val order_readyTime: TextView = view.findViewById(R.id.textView_EditOrder_pickup_time)
        val order_orderDetails: TextView = view.findViewById(R.id.textView_EditOrder_orderDetails)
        val order_itemsCount: TextView = view.findViewById(R.id.textView_EditOrder_itemCount)
        val order_item: TextView = view.findViewById(R.id.textView_EditOrder_displayItem)
        val order_subtotal: TextView = view.findViewById(R.id.textView_EditOrder_subtotal)
        val order_tax: TextView = view.findViewById(R.id.textView_EditOrder_tax)
        val order_total: TextView = view.findViewById(R.id.textView_EditOrder_total)
        val order_address: TextView = view.findViewById(R.id.textView__EditOrder_locationAddress)
        val btnInProcess: Button = view.findViewById(R.id.button_EditOrder_inProcess)
        val btnComplete: Button = view.findViewById(R.id.button_EditOrder_complete)
        val btnCancel: Button = view.findViewById(R.id.button_EditOrder_cancelOrder)

        // parse document to populate fields
        val selectedOrderDocument = selectedOrderId?.let { it ->
            db.collection("ORDERS")
                .document(it).get()
                .addOnSuccessListener {
                    if(it != null){
                        Log.d(TAG, "DocumentSnapshot data: ${it.data}")

                        val timeStamp = it.get("order_time") as Timestamp
                        var date: java.util.Date = timeStamp.toDate()
                        var itemCount = it.get("order_itemCount").toString()
                        var subtotal = it.get("order_subtotal").toString()
                        var tax = it.get("order_tax").toString()
                        var total = it.get("order_total").toString()
                        var address = it.get("restaurant_location").toString()


                        order_readyTime.text = date.toString()
                        order_itemsCount.text = itemCount
//                        order_item.text = it.get("").toString()
                        order_subtotal.text = subtotal
                        order_tax.text = tax
                        order_total.text = total
                        order_address.text = address
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }


        btnInProcess.setOnClickListener {setStatusInProcess()}

        btnComplete.setOnClickListener {setStatusComplete()}

        btnCancel.setOnClickListener {setStatusCancled()}





        return view
    }

    private fun setStatusInProcess(){

        selectedOrderId = arguments?.getString("order_id")

        db.collection("ORDERS")
            .document(selectedOrderId.toString())
            .update("order_status", "In Process")
            .addOnSuccessListener { Log.d(TAG, "Order successfully updated") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating order\n Order ID: $selectedOrderId", e) }

    }

    private fun setStatusComplete(){

        selectedOrderId = arguments?.getString("order_id")

        db.collection("ORDERS")
            .document(selectedOrderId.toString())
            .update("order_status", "Completed")
            .addOnSuccessListener { Log.d(TAG, "Order successfully updated") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating order\n Order ID: $selectedOrderId", e) }

    }

    private fun setStatusCancled(){

        selectedOrderId = arguments?.getString("order_id")

        db.collection("ORDERS")
            .document(selectedOrderId.toString())
            .update("order_status", "Canceled")
            .addOnSuccessListener { Log.d(TAG, "Order successfully updated") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating order\n Order ID: $selectedOrderId", e) }

    }

}