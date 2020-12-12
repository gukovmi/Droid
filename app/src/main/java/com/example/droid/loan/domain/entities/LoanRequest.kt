package com.example.droid.loan.domain.entities

data class LoanRequest(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)