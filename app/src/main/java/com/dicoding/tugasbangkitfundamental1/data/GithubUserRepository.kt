package com.dicoding.tugasbangkitfundamental1.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserDao
import com.dicoding.tugasbangkitfundamental1.data.local.FavUserEntity
import com.dicoding.tugasbangkitfundamental1.data.remote.response.DetailUserResponse
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users
import com.dicoding.tugasbangkitfundamental1.data.remote.retrofit.ApiService

class GithubUserRepository private constructor(
    private val apiService: ApiService,
    private val favUserDao: FavUserDao
) {
    fun findUser(username: String): LiveData<Hasil<List<Users>>> = liveData {
        emit(Hasil.Loading)
        try {
            val response = apiService.getUsers(username)
            val users = response.items
            if (users == null) emit(Hasil.Empty)
            else emit(Hasil.Success(users))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getAllFavUser: ${e.message.toString()}")
            emit(Hasil.Error(e.message.toString()))
        }
    }

    fun getFollowings(username: String): LiveData<Hasil<List<Users>>> = liveData {
        Log.d("Github User repository", "getFollowing: $username")
        emit(Hasil.Loading)
        try {
            val response = apiService.getFollowing(username)
            if (response.isEmpty()) emit(Hasil.Empty)
            else emit(Hasil.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowing: ${e.message.toString()}")
            emit(Hasil.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Hasil<List<Users>>> = liveData {
        emit(Hasil.Loading)
        try {
            val response = apiService.getFollowers(username)
            if (response.isEmpty()) emit(Hasil.Empty)
            else emit(Hasil.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollower: ${e.message.toString()}")
            emit(Hasil.Error(e.message.toString()))
        }

    }

    fun getDetailUsers(username: String): LiveData<Hasil<DetailUserResponse>> = liveData {
        emit(Hasil.Loading)
        try {
            val response = apiService.getDetailUsers(username)
            emit(Hasil.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getDetailUsers: ${e.message.toString()}")
            emit(Hasil.Error(e.message.toString()))
        }
    }

    fun getAllFavorite(): LiveData<List<FavUserEntity>> {
        return favUserDao.getAllFavUser()
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return favUserDao.isFavUserExist(username)
    }

    suspend fun addFavorite(user: FavUserEntity) {
        favUserDao.addFavUser(user)
    }

    suspend fun deleteFavorite(username: String) {
        favUserDao.deleteFavUser(username)
    }

    companion object {
        @Volatile
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavUserDao,
            dataStore: SettingPreference
        ): GithubUserRepository =
            instance ?: synchronized(this) {
                instance ?: GithubUserRepository(apiService, favUserDao)
            }.also { instance = it }
    }
}