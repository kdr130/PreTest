package com.example.pretest.data

import com.example.pretest.model.User

interface UserRepository {
    suspend fun searchUser(query: String):List<User>?
}