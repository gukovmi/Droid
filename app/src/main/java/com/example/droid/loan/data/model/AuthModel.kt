package com.example.droid.loan.data.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)