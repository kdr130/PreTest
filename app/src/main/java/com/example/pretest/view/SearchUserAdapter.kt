package com.example.pretest.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pretest.model.User
import com.example.pretest.view.UserViewHolder.Companion.create

class SearchUserAdapter(private var userList: List<User>): RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = create(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}