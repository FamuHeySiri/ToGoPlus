package com.isaiah.rattlerbees.fragments.admin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import java.util.*
import java.text.SimpleDateFormat as SimpleDateFormat

class AdminViewOrdersFragment : Fragment(), DatePickerDialog.OnDateSetListener{


    private companion object {
        private const val TAG = "ADMIN_VIEW_ORDERS_FRAGMENT"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val orders_query = db
        .collection("ORDERS")
        .orderBy("order_time", Query.Direction.DESCENDING)
        .limit(50)
    val options = FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(orders_query, OrdersModel::class.java)
        .setLifecycleOwner(this).build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_view_orders, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val minDate: TextView? = getView()?.findViewById<TextView>(R.id.tv_minDate)
        val maxDate: TextView? = getView()?.findViewById<TextView>(R.id.tv_maxDate)
        val btnMinDate: Button? = getView()?.findViewById<Button>(R.id.button_adminOrders_minDate)
        val btnMaxDate: Button? = getView()?.findViewById<Button>(R.id.button_adminOrders_maxDate)
        val btnSubmitDate: Button? = getView()?.findViewById<Button>(R.id.button_admin_submitDateSearch)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_admin_view_orders)


//        minDate?.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        val cal = Calendar.getInstance()
        val minDateSelected: Date
        val maxDateSelected: Date

        val minDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            val myFormat = "MM.dd.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            minDate?.text = sdf.format(cal.time)

        }

        val maxDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            val myFormat = "MM.dd.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            maxDate?.text = sdf.format(cal.time)

        }


        btnMinDate?.setOnClickListener {
            Toast.makeText(context, "minDate", Toast.LENGTH_SHORT).show()

            context?.let { it1 ->
                DatePickerDialog(it1, minDateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()


            }

        }

        btnMaxDate?.setOnClickListener {
            Toast.makeText(context, "maxDate", Toast.LENGTH_SHORT).show()

            context?.let { it2 ->
                DatePickerDialog(it2, maxDateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }

        btnSubmitDate?.setOnClickListener {
            Toast.makeText(context, "submit search", Toast.LENGTH_SHORT).show()
        }

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = AdminViewOrdersAdapter(options)
        }

        if (recyclerView != null) {
            changeQuery(recyclerView)
        }

    }


    fun changeQuery(recyclerView: RecyclerView){

        val minDate = Date(2020, 5, 1)
        val maxDate: Date = Date(2020, 8, 1 )


        val newQuery = db
                .collection("ORDERS")
                .whereGreaterThan("order_time", minDate)
                .whereGreaterThan("order_time", maxDate)
                .get()



    }

    inner class AdminViewOrdersAdapter(options: FirestoreRecyclerOptions<OrdersModel>) : FirestoreRecyclerAdapter<OrdersModel, AdminViewOrdersFragment.AdminOrdersViewHolder>(options){




        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewOrdersFragment.AdminOrdersViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_order, parent, false)
            return AdminOrdersViewHolder(view)
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        override fun onBindViewHolder(holder: AdminViewOrdersFragment.AdminOrdersViewHolder, position: Int, model: OrdersModel) {

            val order_id:  TextView = holder.itemView.findViewById(R.id.orders_card_order_id)
            val order_time: TextView = holder.itemView.findViewById(R.id.orders_card_order_time)
            val order_status: TextView = holder.itemView.findViewById(R.id.orders_card_order_status)
            val user_name: TextView = holder.itemView.findViewById(R.id.orders_card_customer_name)
            val order_rating: RatingBar = holder.itemView.findViewById(R.id.rating_bar)

            order_rating.setIsIndicator(true)

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
            order_rating.rating = model.order_rating.toFloat()

        }


    }


    inner class AdminOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

}