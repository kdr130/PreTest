package com.example.pretest.data

import android.util.Log
import com.example.pretest.api.GithubService
import com.example.pretest.model.User

class GithubRepository(private val githubService: GithubService) : UserRepository {
    private var pageNo = 1
    override suspend fun searchUser(query: String): List<User> {
        val response = githubService.searchUser(query, pageNo, DEFAULT_PAGE_SIZE)
        Log.d(TAG, "response: ${response.body()}")

        return response.body()?.items ?: emptyList()
    }

    companion object {
        private const val TAG = "GithubRepository"
        const val DEFAULT_PAGE_SIZE = 10
    }
}