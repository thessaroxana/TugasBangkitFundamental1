package com.dicoding.tugasbangkitfundamental1.data.di

import android.content.Context
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserDatabase
import com.dicoding.tugasbangkitfundamental1.data.remote.retrofit.ApiConfig

object Injection {
    fun provideInjection(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavUserDatabase.getInstance(context)
        val dao = database.favUserDao()
        return GithubUserRepository.getInstance(apiService, dao)
    }
}