package com.example.pretest.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pretest.data.UserRepository
import androidx.lifecycle.viewModelScope
import com.example.pretest.model.User
import kotlinx.coroutines.launch

class SearchUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showUserList: MutableLiveData<Boolean> = MutableLiveData()
    val showNoResult: MutableLiveData<Boolean> = MutableLiveData()

    var userList : MutableLiveData<List<User>> = MutableLiveData()

    init {
        showLoading.value = false
        showUserList.value = true
        showNoResult.value = false
    }

    fun search(query: String) {
        showNoResult.value = false
        showLoading.value = true

        viewModelScope.launch {
            val result = userRepository.searchUser(query)
            userList.postValue(result)

            showLoading.postValue(false)

            if (result == null || result.isEmpty()) {
                showNoResult.postValue(true)
            } else {
                showUserList.postValue(true)
            }
        }
    }
}