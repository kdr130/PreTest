package com.example.pretest.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pretest.api.GithubService
import com.example.pretest.model.User
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val githubService: GithubService) : UserRepository {

    override fun searchUser(query: String): Flow<PagingData<User>> {
        Log.d(TAG, "query: $query")
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(githubService, query) }
        ).flow
    }

    companion object {
        private const val TAG = "GithubRepository"
        const val DEFAULT_PAGE_SIZE = 10
    }
}