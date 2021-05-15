package com.example.pretest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pretest.data.UserRepository

class SearchUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showUserList: MutableLiveData<Boolean> = MutableLiveData()

    init {
        showLoading.value = false
        showUserList.value = true
    }

    fun search(query: String) {
        userRepository.searchUser(query)
    }
}