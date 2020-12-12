package com.example.droid.loan.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "loans")
data class DataLoan(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("date")
    var date: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("id")
    @PrimaryKey
    val id: Long,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("percent")
    val percent: Double,
    @SerializedName("period")
    val period: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("state")
    val state: String
)