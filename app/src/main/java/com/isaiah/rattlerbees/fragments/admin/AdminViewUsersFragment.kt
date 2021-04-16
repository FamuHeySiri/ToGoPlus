package com.isaiah.rattlerbees.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.UserModel
import com.isaiah.rattlerbees.utilities.Communicator
import com.squareup.picasso.Picasso

class AdminViewUsersFragment : Fragment() {

    private companion object {
        private const val TAG = "DISPLAY_USERS"
    }

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    // create object of our interface
    private lateinit var communicator: Communicator

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    // query for users collection
    val user_query = db.collection("USERS")
    val options = FirestoreRecyclerOptions.Builder<UserModel>().setQuery(user_query, UserModel::class.java)
        .setLifecycleOwner(this).build()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_view_users, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recyclerview_admin_view_users)

        // assign layout manager and adapter to the view
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = AdminViewUserAdapter(options)
        }
    }


    inner class AdminViewUserAdapter(options: FirestoreRecyclerOptions<UserModel>) : FirestoreRecyclerAdapter<UserModel, AdminUserViewHolder>(options){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminUserViewHolder {
            // inflate individual row layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_user, parent, false)
            return AdminUserViewHolder(view)
        }

        override fun onBindViewHolder(holder: AdminUserViewHolder, position: Int, model: UserModel) {

            val user_name: TextView = holder.itemView.findViewById(R.id.user_name)
            val user_account: TextView = holder.itemView.findViewById(R.id.user_account_type)
            val user_email: TextView = holder.itemView.findViewById(R.id.user_email)
            val user_image: ImageView = holder.itemView.findViewById(R.id.user_profile_image)

            val fullName = model.user_firstName + " " + model.user_lastName

            user_name.text = fullName
            user_account.text = model.user_accountType
            user_email.text = model.user_email

            if (model.user_photoURL.isEmpty()) {
                user_image.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                Picasso.get().load(model.user_photoURL).into(user_image)
            }

        }
    }


    inner class AdminUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View){

            communicator = activity as Communicator

            val position: Int = adapterPosition
            val userId: String = options.snapshots.get(position).user_uid

            if (position != RecyclerView.NO_POSITION){

                val document = user_query.addSnapshotListener {
                        snapshot, error ->

                    if (error != null){
                        // handle error
                    }
                    if (snapshot != null){
                        // handle snapshot
//                        Toast.makeText(context, userId, Toast.LENGTH_LONG).show()

                        communicator.passDataCom(userId)
                    }

                }



            }
        }
    }


//    interface OnItemClickListener {
//        fun onItemClick(posistion: Int)
//    }

}