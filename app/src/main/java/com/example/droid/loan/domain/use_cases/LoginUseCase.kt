package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(auth: Auth): Single<String> =
        loanRepository.login(auth)
}