package com.example.pretest.data

import androidx.paging.PagingData
import com.example.pretest.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun searchUser(query: String): Flow<PagingData<User>>
}