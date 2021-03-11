package com.isaiah.rattlerbees.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.isaiah.rattlerbees.R
import com.isaiah.rattlerbees.models.UserModel
import com.isaiah.rattlerbees.utilities.UserViewHolder

class FirestoreAdapter(options: FirestoreRecyclerOptions<UserModel>) : FirestoreRecyclerAdapter<UserModel, UserViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_card, parent, false)
        return UserViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: UserModel) {

        val user_name: TextView = holder.itemView.findViewById(R.id.user_name)
        val user_account: TextView = holder.itemView.findViewById(R.id.user_account_type)
        val user_email: TextView = holder.itemView.findViewById(R.id.user_email)
        val user_image: ImageView = holder.itemView.findViewById(R.id.user_profile_image)

        user_name.text = model.user_firstName + " " + model.user_lastName
        user_account.text = model.user_accountType
        user_email.text = model.user_email
        // TODO: 3/10/21 insert user image from data store
//        user_image.setImageResource(images[position])
    }


}