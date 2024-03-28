package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import com.dicoding.tugasbangkitfundamental1.data.Hasil
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserEntity
import com.dicoding.tugasbangkitfundamental1.data.remote.response.DetailUserResponse
import com.dicoding.tugasbangkitfundamental1.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    private val _userEntity = MutableLiveData<FavUserEntity>()
    val favUserEntity: LiveData<FavUserEntity> = _userEntity

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUsers = MutableLiveData<DetailUserResponse>()
    val detailUsers: LiveData<DetailUserResponse> = _detailUsers

    companion object {
        private const val TAG = "DetailViewModel"
    }
    fun addFavorite(user: FavUserEntity) {
        viewModelScope.launch {
            githubUserRepository.addFavorite(user)
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubUserRepository.deleteFavorite(username)
        }
    }

    fun isFavorite(username: String) = githubUserRepository.isFavorite(username)
    fun getDetailUser(username: String) {
        githubUserRepository.getDetailUsers(username).observeForever { result ->
            when (result) {
                is Hasil.Loading -> {
                    _isLoading.value = true
                }

                is Hasil.Success -> {
                    _isLoading.value = false
                    _detailUsers.value = result.data
                    _userEntity.value = FavUserEntity(0, result.data.login, result.data.avatarUrl)
                }

                is Hasil.Error -> {
                    _isLoading.value = false
                }

                is Hasil.Empty -> {
                    _isLoading.value = false
                }
            }
        }
    }
}