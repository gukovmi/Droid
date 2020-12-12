package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.entities.LoanConditions
import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(token: String): Single<LoanConditions> =
        loanRepository.getLoanConditions(token)
}