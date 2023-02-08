package com.mano.mvvmretrofitexample.models

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("id")
    var `$id`: String,
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String

) {
    data class Data(
        @SerializedName("id")
        val `$id`: String,
        @SerializedName("Email")
        val Email: String,
        @SerializedName("Id")
        val Id: Int,
        @SerializedName("Name")
        val Name: String,
        @SerializedName("Token")
        val Token: String
    )
}