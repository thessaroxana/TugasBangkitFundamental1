package com.dicoding.tugasbangkitfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.tugasbangkitfundamental1.data.response.DetailUserResponse
import com.dicoding.tugasbangkitfundamental1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUsers = MutableLiveData<DetailUserResponse>()
    val detailUsers: LiveData<DetailUserResponse> = _detailUsers

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUsers.value = response.body()!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}