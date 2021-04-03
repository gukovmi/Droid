package com.example.droid.loan.domain.usecase.auth

import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.repository.AuthRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(auth: Auth): Single<String> =
        authRepository.login(auth)
}