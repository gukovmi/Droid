package com.example.droid.loan.data.network.api

import com.example.droid.loan.data.model.LoanConditionsModel
import com.example.droid.loan.data.model.LoanModel
import com.example.droid.loan.data.model.LoanRequestModel
import io.reactivex.Single
import retrofit2.http.*

interface LoanApi {
    @GET("loans/all")
    fun getLoans(
        @Header("Authorization") token: String
    ): Single<List<LoanModel>>

    @POST("loans/")
    fun createLoan(
        @Header("Authorization") token: String,
        @Body loanRequestModel: LoanRequestModel
    ): Single<LoanModel>

    @GET("loans/{id}")
    fun getLoanById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Single<LoanModel>

    @GET("loans/conditions")
    fun getLoanConditions(@Header("Authorization") token: String): Single<LoanConditionsModel>
}