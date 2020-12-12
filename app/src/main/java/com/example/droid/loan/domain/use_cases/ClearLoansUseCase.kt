package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Completable
import javax.inject.Inject

class ClearLoansUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(): Completable =
        loanRepository.clearLoans()
}