package com.example.droid.loan.data.model

import com.google.gson.annotations.SerializedName

data class DataLoanRequest(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("percent")
    val percent: Double,
    @SerializedName("period")
    val period: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)