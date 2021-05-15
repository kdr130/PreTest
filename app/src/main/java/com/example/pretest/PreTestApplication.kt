package com.example.pretest

import android.app.Application
import com.example.pretest.koin.githubApiModule
import com.example.pretest.koin.repositoryModule
import com.example.pretest.koin.searchUserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PreTestApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PreTestApplication)
            modules(listOf(
                githubApiModule,
                repositoryModule,
                searchUserViewModel
            ))
        }
    }
}