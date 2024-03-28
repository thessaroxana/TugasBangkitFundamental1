package com.dicoding.tugasbangkitfundamental1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import com.dicoding.tugasbangkitfundamental1.data.SettingPreference
import com.dicoding.tugasbangkitfundamental1.data.dataStore
import com.dicoding.tugasbangkitfundamental1.data.di.Injection

class ViewModelFactory private constructor(
    private val preference: SettingPreference, private val githubUserRepository: GithubUserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainViewModel::class.java -> MainViewModel(preference, githubUserRepository)
        DetailViewModel::class.java -> DetailViewModel(githubUserRepository)
        FavoriteViewModel::class.java -> FavoriteViewModel(githubUserRepository)
        FollowViewModel::class.java -> FollowViewModel(githubUserRepository)
        SettingViewModel::class.java -> SettingViewModel(preference)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    SettingPreference.getInstance(application.dataStore),
                    Injection.provideInjection(application.applicationContext)
                )
            }.also { instance = it }
    }
}