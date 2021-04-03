package com.example.droid.loan.domain.usecase.loan

import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Completable
import javax.inject.Inject

class ClearLoansUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(): Completable =
        loanRepository.clearLoans()
}