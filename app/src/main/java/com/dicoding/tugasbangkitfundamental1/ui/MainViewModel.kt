package com.dicoding.tugasbangkitfundamental1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.tugasbangkitfundamental1.data.response.GithubResponse
import com.dicoding.tugasbangkitfundamental1.data.response.Users
import com.dicoding.tugasbangkitfundamental1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _user = MutableLiveData<List<Users>>()
    val listUser: LiveData<List<Users>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("Thessa")
    }

    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: retrofit2.Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "onResponse: ${response.body()?.items}")
                    _isEmpty.value = response.body()?.items!!.isEmpty()
                    _user.value = response.body()?.items
                } else {
                    _errorMessage.value = "Gagal mengambil data: ${response.message()}"
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Terjadi kesalahan jaringan: ${t.message.toString()}"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}