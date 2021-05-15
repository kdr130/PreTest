package com.example.pretest.koin

import com.example.pretest.api.GithubService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val GITHUB_BASE_URL = "https://api.github.com/"

val githubApiModule = module {
    factory { provideGithubApi(get()) }
    single { provideRetrofit() }
}

fun provideGithubApi(retrofit: Retrofit): GithubService = retrofit.create(GithubService::class.java)

fun provideRetrofit(): Retrofit {
    val gsonBuilder = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setLenient()
        .create()

    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    return Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
        .client(client)
        .build()
}