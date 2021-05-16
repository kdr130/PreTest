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
    val showErrorMsg: MutableLiveData<Boolean> = MutableLiveData()
    val errorMsg: MutableLiveData<String> = MutableLiveData()

    private var currentQueryStr: String? = null
    private var currentSearchResult: Flow<PagingData<User>>? = null
    private var isStatusError: Boolean = false

    init {
        showLoading.value = false
        showUserList.value = true
        showNoResult.value = false
    }

    fun search(query: String): Flow<PagingData<User>> {
        val lastResult = currentSearchResult
        if (query == currentQueryStr && lastResult != null && !isStatusError) {
            return lastResult
        }
        currentQueryStr = query
        val result: Flow<PagingData<User>> = userRepository.searchUser(query)
            .cachedIn(viewModelScope)
        currentSearchResult = result
        return result
    }

    fun setLoadState(loadState: CombinedLoadStates, itemCount: Int) {
        isStatusError = isErrorState(loadState)
        showErrorMsg.postValue(isStatusError)

        val errorMsgFromState = getErrorMsg(loadState)
        errorMsg.postValue(errorMsgFromState)

        showNoResult.postValue(loadState.refresh is LoadState.NotLoading && itemCount == 0)

        showUserList.postValue(loadState.refresh is LoadState.NotLoading && !isStatusError)

        showLoading.postValue(
            loadState.source.refresh is LoadState.Loading
                    || (loadState.source.append is LoadState.Loading && itemCount > 0)
        )
    }

    private fun isErrorState(loadState: CombinedLoadStates): Boolean {
        return loadState.refresh is LoadState.Error || loadState.append is LoadState.Error || loadState.prepend is LoadState.Error
    }

    private fun getErrorMsg(loadState: CombinedLoadStates): String = when {
        loadState.prepend is LoadState.Error -> {
            (loadState.prepend as LoadState.Error).error.message ?: ""
        }
        loadState.append is LoadState.Error -> {
            (loadState.append as LoadState.Error).error.message ?: ""
        }
        loadState.refresh is LoadState.Error -> {
            (loadState.refresh as LoadState.Error).error.message ?: ""
        }
        else -> ""
    }
}