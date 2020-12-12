package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.entities.UserEntity
import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(auth: Auth): Single<UserEntity> =
        loanRepository.registration(auth)
}
