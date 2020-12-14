package com.example.droid.loan.domain.usecase

import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.entity.User
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(auth: Auth): Single<User> =
        loanRepository.registration(auth)
}
