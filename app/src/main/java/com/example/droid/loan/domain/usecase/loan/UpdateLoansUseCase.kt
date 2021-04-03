package com.example.droid.loan.domain.usecase.loan

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateLoansUseCase @Inject constructor(private val loanRepository: LoanRepository) {
    operator fun invoke(token: String): Single<List<Loan>> =
        loanRepository.updateLoans(token)
}