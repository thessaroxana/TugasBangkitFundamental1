package com.dicoding.tugasbangkitfundamental1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.tugasbangkitfundamental1.data.GithubUserRepository
import com.dicoding.tugasbangkitfundamental1.data.Hasil
import com.dicoding.tugasbangkitfundamental1.data.SettingPreference
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users


class MainViewModel(
    private val preference: SettingPreference,
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {
    private val _user = MutableLiveData<List<Users>>()
    val listUser: LiveData<List<Users>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty


    init {
        findUser("Thessa")
    }

    fun findUser(username: String) {
        githubUserRepository.findUser(username).observeForever { result ->
            when (result) {
                is Hasil.Loading -> {
                    _isLoading.value = true
                    _isEmpty.value = false
                }

                is Hasil.Success -> {
                    _isLoading.value = false
                    _isEmpty.value = false
                    _user.value = result.data
                }

                is Hasil.Error -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }

                is Hasil.Empty -> {
                    _isLoading.value = false
                    _isEmpty.value = true
                }
            }
        }
    }
    fun getThemeSetting(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

//    companion object {
//        private const val TAG = "MainViewModel"
//    }
}