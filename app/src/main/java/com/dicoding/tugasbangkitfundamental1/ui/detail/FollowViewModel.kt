package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.tugasbangkitfundamental1.data.response.Users
import com.dicoding.tugasbangkitfundamental1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
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
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("FollowViewModel", "onResponse: ${response.body()}")
                    _isEmpty.value = response.body()!!.isEmpty()
                    _listFollowers.value = response.body()
                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("FollowViewModel", "onResponse: ${response.body()}")
                    _isEmpty.value = response.body()!!.isEmpty()
                    _listFollowing.value = response.body()!!
                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "FollowViewModel"
    }
}