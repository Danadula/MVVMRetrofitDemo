package com.mano.mvvmretrofitexample.models

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)
