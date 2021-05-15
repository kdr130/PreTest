package com.example.pretest.koin

import com.example.pretest.SearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val searchUserViewModel = module {
    viewModel { SearchUserViewModel(get()) }
}
