package com.mano.mvvmretrofitexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mano.mvvmretrofitexample.api.BaseResponse
import com.mano.mvvmretrofitexample.models.RegisterUserRequest
import com.mano.mvvmretrofitexample.models.RegisterUserResponse
import com.mano.mvvmretrofitexample.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    application: Application,
    private val repository: MainRepository
) : AndroidViewModel(application) {

    val registrationResult: MutableLiveData<BaseResponse<RegisterUserResponse>> = MutableLiveData()

    /**
     * Do Registration Function
     */
    fun doRegistration(name: String, email: String, pwd: String) {

        registrationResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val request = RegisterUserRequest(
                    name = name,
                    email = email,
                    password = pwd
                )

                val response = repository.userRegistration(registerUserRequest = request)

                if (response.code() == 200) {
                    registrationResult.value = BaseResponse.Success(response.body())
                } else {
                    registrationResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                registrationResult.value = BaseResponse.Error(ex.message)
            }
        }

    }
}