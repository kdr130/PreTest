package com.example.pretest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pretest.R
import com.example.pretest.databinding.ItemUserBinding
import com.example.pretest.model.User

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User?) {
        user?.let {
            binding.model = it
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val binding: ItemUserBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
            return UserViewHolder(binding)
        }
    }
}