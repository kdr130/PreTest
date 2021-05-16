package com.example.pretest.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pretest.data.UserRepository
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pretest.model.User
import kotlinx.coroutines.flow.Flow

class SearchUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showUserList: MutableLiveData<Boolean> = MutableLiveData()
    val showNoResult: MutableLiveData<Boolean> = MutableLiveData()

    private var currentQueryStr: String? = null
    private var currentSearchResult: Flow<PagingData<User>>? = null

    init {
        showLoading.value = false
        showUserList.value = true
        showNoResult.value = false
    }

    fun search(query: String): Flow<PagingData<User>> {
        val lastResult = currentSearchResult
        if (query == currentQueryStr && lastResult != null) {
            return lastResult
        }
        currentQueryStr = query
        val result: Flow<PagingData<User>> = userRepository.searchUser(query)
            .cachedIn(viewModelScope)
        currentSearchResult = result
        return result
    }

    fun setLoadState(loadState: CombinedLoadStates, itemCount: Int) {
        showNoResult.postValue(loadState.refresh is LoadState.NotLoading && itemCount == 0)
        showUserList.postValue(loadState.source.refresh is LoadState.NotLoading)
        showLoading.postValue(loadState.source.refresh is LoadState.Loading)
    }
}