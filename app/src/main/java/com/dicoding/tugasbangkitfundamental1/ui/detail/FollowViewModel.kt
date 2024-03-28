package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users
import com.dicoding.tugasbangkitfundamental1.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<Users>>()
    val listFollowers: LiveData<List<Users>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<Users>>()
    val listFollowing: LiveData<List<Users>> = _listFollowing

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun getFollowers(username: String) {
        githubUserRepository.getFollowers(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listFollowers.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }

                is Result.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }

        }
    }

    fun getFollowing(username: String) {
        githubUserRepository.getFollowings(username).observeForever { result ->
            when (result) {
                is Result.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }

                is Result.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _listFollowing.value = result.data

                }

                is Result.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }

                is Result.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }
        }
    }

    companion object {
        const val TAG = "FollowViewModel"
    }
}