package com.mano.mvvmretrofitexample.repository

import com.mano.mvvmretrofitexample.api.ApiInterface
import com.mano.mvvmretrofitexample.models.LoginRequest
import com.mano.mvvmretrofitexample.models.LoginResponse
import com.mano.mvvmretrofitexample.models.RegisterUserRequest
import com.mano.mvvmretrofitexample.models.RegisterUserResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiInterface.loginUser(loginRequest = loginRequest)
    }

    suspend fun userRegistration(registerUserRequest: RegisterUserRequest): Response<RegisterUserResponse> {
        return apiInterface.registerUser(registerUserRequest = registerUserRequest)
    }

}