package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.entities.Loan
import com.example.droid.loan.domain.entities.LoanRequest
import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(token: String, loanRequest: LoanRequest): Single<Loan> =
        loanRepository.createLoan(token, loanRequest)
}