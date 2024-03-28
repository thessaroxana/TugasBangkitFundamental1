package com.dicoding.tugasbangkitfundamental1.data.local

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

interface FavUserDao {
    @Insert
    suspend fun addFavUser(user: FavUserEntity)

    @Query("DELETE FROM user WHERE username = :username")
    suspend fun deleteFavUser(username: String)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllFavUser(): LiveData<List<FavUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun isFavUserExist(username: String): LiveData<Boolean>
}