package com.example.droid.loan.data.models

import com.google.gson.annotations.SerializedName

data class DataLoanConditions(
    @SerializedName("percent")
    val percent: Double,
    @SerializedName("period")
    val period: Int,
    @SerializedName("maxAmount")
    val maxAmount: Long
)