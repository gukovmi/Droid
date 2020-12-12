package com.example.droid.loan.data.models

import com.google.gson.annotations.SerializedName

data class DataAuth(
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)