package com.isaiah.rattlerbees.fragments.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.isaiah.rattlerbees.models.OrdersModel


class ViewUserDetailFragment : Fragment() {

    // Identification tag for fragment
    private companion object {
        const val TAG = "USER_DETAILS"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // Stores the user_uid passed from bundle
    var selectedUserId: String? = ""

    var accountIsActive: Boolean = true


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_view_user_detail, container, false)

        selectedUserId = arguments?.getString("user_uid")

        var selectedUserAccountType: String = ""

        val user_name: TextView = view.findViewById(R.id.detail_user_name)
        val user_email: TextView = view.findViewById(R.id.detail_user_email)
        val user_account: TextView = view.findViewById(R.id.detail_user_account_type)
        val button_delete: Button = view.findViewById(R.id.button_delete_user_account)
        // TODO: 3/21/21  import selected user image
//        val user_image:  = view.findViewById(R.id.detail_user_profile_image)

        // Parse selected user document and populate fields
        val selectedUserDocument = selectedUserId?.let { it ->
            db.collection("USERS").document(it).get()
                    .addOnSuccessListener {
                        if(it != null){
                            Log.d(TAG, "DocumentSnapshot data: ${it.data}")

                            selectedUserAccountType = it.get("user_accountType").toString()

                            val firstname = it.get("user_firstName")
                            val lastname = it.get("user_lastName")
                            val status: Boolean? = it.getBoolean("isActive")
                            if (status != null) {
                                accountIsActive = status
                            }
                            val email = it.get("user_email").toString()
                            val accountType = it.get("user_accountType").toString()
                            var info: String = ""

                            if (accountType == "CUSTOMER"){
                                info = " $accountType - Active: $status"
                            } else {
                                info = accountType
                            }

                            user_name.text = "$firstname $lastname"
                            user_email.text = email
                            user_account.text = info


                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener{ exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
        }

        button_delete.setOnClickListener {
            // TODO: 3/21/21 action for deactiving selected user

            if (selectedUserAccountType == "CUSTOMER"){

                if (accountIsActive) {
                    deactivateUser()
                } else {
                    reactivateUser()
                }

            } else {
                Log.d(TAG, "User account can't be deactivated through application")
                Log.d(TAG, "User account can't be deactivated through application")
                Log.d(TAG, "Contact ")
            }

        }

       return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        selectedUserId = arguments?.getString("user_uid")

        // query for users collection
        val orders_query = db
                .collection("ORDERS")
                .whereEqualTo("user_id", selectedUserId.toString())
                .orderBy("order_time", Query.Direction.DESCENDING)

        var options = FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(orders_query, OrdersModel::class.java)
                .setLifecycleOwner(this).build()

        super.onActivityCreated(savedInstanceState)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_detail_user)

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = DetailUserAdapter(options)
        }

    }


    /*
    * Code for the selected users order history
    *
    *
     */

    inner class DetailUserAdapter(options: FirestoreRecyclerOptions<OrdersModel>) : FirestoreRecyclerAdapter<OrdersModel, ViewUserDetailFragment.DetailUserViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewUserDetailFragment.DetailUserViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_order, parent, false)
            return DetailUserViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewUserDetailFragment.DetailUserViewHolder, position: Int, model: OrdersModel) {


            selectedUserId = arguments?.getString("user_uid")

            val order_id:  TextView = holder.itemView.findViewById(R.id.orders_card_order_id)
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


    inner class DetailUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }



    private fun deactivateUser() {

        selectedUserId = arguments?.getString("user_uid")

        db.collection("USERS")
                .document(selectedUserId.toString())
                .update("isActive", false)
                .addOnSuccessListener { Log.d(TAG, "User successfully deactivated") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deactivating user $selectedUserId", e) }



    }


    private fun reactivateUser() {
        selectedUserId = arguments?.getString("user_uid")

        db.collection("USERS")
                .document(selectedUserId.toString())
                .update("isActive", true)
                .addOnSuccessListener { Log.d(TAG, "User successfully reactivated") }
                .addOnFailureListener { e -> Log.w(TAG, "Error reactivating user $selectedUserId", e) }


    }



}