package com.dicoding.tugasbangkitfundamental1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    fun getAllFavorite() = githubUserRepository.getAllFavorite()
    fun deleteAllFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }
}