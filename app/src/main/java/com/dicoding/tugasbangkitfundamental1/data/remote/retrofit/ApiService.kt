package com.dicoding.tugasbangkitfundamental1.data.remote.retrofit

import com.dicoding.tugasbangkitfundamental1.data.remote.response.DetailUserResponse
import com.dicoding.tugasbangkitfundamental1.data.remote.response.GithubResponse
import com.dicoding.tugasbangkitfundamental1.data.remote.response.Users
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String,
    ):GithubResponse

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String,
    ):DetailUserResponse

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ) : List<Users>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ) : List<Users>
}