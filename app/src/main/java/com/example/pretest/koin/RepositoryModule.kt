package com.example.pretest.koin

import com.example.pretest.data.GithubRepository
import com.example.pretest.data.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserRepository> { GithubRepository(get()) }
}
