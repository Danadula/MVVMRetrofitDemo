package com.mano.mvvmretrofitexample.api

import com.mano.mvvmretrofitexample.models.LoginRequest
import com.mano.mvvmretrofitexample.models.LoginResponse
import com.mano.mvvmretrofitexample.models.RegisterUserRequest
import com.mano.mvvmretrofitexample.models.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("/api/authaccount/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/authaccount/registration")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<RegisterUserResponse>

}