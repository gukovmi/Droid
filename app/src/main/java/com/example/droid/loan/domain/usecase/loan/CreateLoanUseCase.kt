package com.example.droid.loan.domain.usecase.loan

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.LoanRequest
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(token: String, loanRequest: LoanRequest): Single<Loan> =
        loanRepository.createLoan(token, loanRequest)
}