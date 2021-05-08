package com.example.droid.loan.domain.repository

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.LoanConditions
import com.example.droid.loan.domain.entity.LoanRequest
import io.reactivex.Completable
import io.reactivex.Single

interface LoanRepository {
    fun getLoans(token: String): Single<List<Loan>>
    fun createLoan(token: String, loanRequest: LoanRequest): Single<Loan>
    fun getLoanById(token: String, id: Long): Single<Loan>
    fun getLoanConditions(token: String): Single<LoanConditions>
    fun updateLoans(token: String): Single<List<Loan>>
    fun clearLoans(): Completable
}