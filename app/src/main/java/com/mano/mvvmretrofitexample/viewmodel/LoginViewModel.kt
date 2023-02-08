package com.mano.mvvmretrofitexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mano.mvvmretrofitexample.api.BaseResponse
import com.mano.mvvmretrofitexample.models.LoginRequest
import com.mano.mvvmretrofitexample.models.LoginResponse
import com.mano.mvvmretrofitexample.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val repository: MainRepository
) : AndroidViewModel(application) {

    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    /**
     * Login user Function
     */
    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {

            try {

                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )

                val response = repository.loginUser(loginRequest = loginRequest)

                if (response.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}