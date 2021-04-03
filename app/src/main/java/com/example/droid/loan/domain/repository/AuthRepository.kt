package com.example.droid.loan.domain.repository

import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.entity.User
import io.reactivex.Single

interface AuthRepository {
    fun registration(auth: Auth): Single<User>
    fun login(auth: Auth): Single<String>
}