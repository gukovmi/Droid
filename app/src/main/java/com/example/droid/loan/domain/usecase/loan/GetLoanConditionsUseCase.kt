package com.example.droid.loan.domain.usecase.loan

import com.example.droid.loan.domain.entity.LoanConditions
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(token: String): Single<LoanConditions> =
        loanRepository.getLoanConditions(token)
}