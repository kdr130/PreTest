package com.example.pretest.view

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pretest.model.User
import com.example.pretest.view.UserViewHolder.Companion.create

class SearchUserAdapter: PagingDataAdapter<User, UserViewHolder>(USER_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = create(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}