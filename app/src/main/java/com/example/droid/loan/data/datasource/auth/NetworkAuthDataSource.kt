package com.example.droid.loan.data.datasource.auth

import com.example.droid.loan.data.model.AuthModel
import com.example.droid.loan.data.model.UserModel
import com.example.droid.loan.data.network.api.AuthApi
import io.reactivex.Single
import javax.inject.Inject

interface NetworkAuthDataSource {
    fun registration(authModel: AuthModel): Single<UserModel>
    fun login(authModel: AuthModel): Single<String>
}

class NetworkAuthDataSourceImpl @Inject constructor(
    private val authApiClient: AuthApi
) : NetworkAuthDataSource {
    override fun registration(authModel: AuthModel): Single<UserModel> =
        authApiClient.registration(authModel)

    override fun login(authModel: AuthModel): Single<String> =
        authApiClient.login(authModel)
}