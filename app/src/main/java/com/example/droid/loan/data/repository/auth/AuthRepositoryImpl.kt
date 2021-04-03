package com.example.droid.loan.data.repository.auth

import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSource
import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.entity.User
import com.example.droid.loan.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkAuthDataSource: NetworkAuthDataSource,
    private val dataConverter: DataConverter
) : AuthRepository {
    override fun registration(auth: Auth): Single<User> =
        networkAuthDataSource.registration(dataConverter.toDataAuth(auth))
            .map {
                dataConverter.fromDataUserEntity(it)
            }

    override fun login(auth: Auth): Single<String> =
        networkAuthDataSource.login(dataConverter.toDataAuth(auth))
}