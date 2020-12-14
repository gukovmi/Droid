package com.example.droid.loan.data.model

import com.google.gson.annotations.SerializedName

data class LoanConditionsModel(
    @SerializedName("percent")
    val percent: Double,
    @SerializedName("period")
    val period: Int,
    @SerializedName("maxAmount")
    val maxAmount: Long
)