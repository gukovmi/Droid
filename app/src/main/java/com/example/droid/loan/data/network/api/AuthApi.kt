package com.example.droid.loan.data.network.api

import com.example.droid.loan.data.model.AuthModel
import com.example.droid.loan.data.model.UserModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("registration")
    fun registration(
        @Body authModel: AuthModel
    ): Single<UserModel>

    @POST("login")
    fun login(
        @Body authModel: AuthModel
    ): Single<String>
}