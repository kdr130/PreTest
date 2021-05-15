package com.example.pretest.data

import com.example.pretest.api.GithubService

class GithubRepository(private val githubService: GithubService): UserRepository {
    override fun searchUser(query: String) {
        TODO("Not yet implemented")
    }
}