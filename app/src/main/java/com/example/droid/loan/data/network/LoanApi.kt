package com.example.droid.loan.data.network

import com.example.droid.loan.data.models.*
import io.reactivex.Single
import retrofit2.http.*

interface LoanApi {
    @POST("registration")
    fun registration(
        @Body auth: DataAuth
    ): Single<DataUserEntity>

    @POST("login")
    fun login(
        @Body auth: DataAuth
    ): Single<String>

    @GET("loans/all")
    fun getLoans(
        @Header("Authorization") token: String
    ): Single<List<DataLoan>>

    @POST("loans/")
    fun createLoan(
        @Header("Authorization") token: String,
        @Body loanRequest: DataLoanRequest
    ): Single<DataLoan>

    @GET("loans/{id}")
    fun getLoanById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Single<DataLoan>

    @GET("loans/conditions")
    fun getLoanConditions(@Header("Authorization") token: String): Single<DataLoanConditions>
}