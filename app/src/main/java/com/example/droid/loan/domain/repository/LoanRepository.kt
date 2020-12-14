package com.example.droid.loan.domain.repository

import com.example.droid.loan.domain.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface LoanRepository {
    fun registration(auth: Auth): Single<User>
    fun login(auth: Auth): Single<String>
    fun getLoans(token: String): Single<List<Loan>>
    fun createLoan(token: String, loanRequest: LoanRequest): Single<Loan>
    fun getLoanById(token: String, id: Long): Single<Loan>
    fun getLoanConditions(token: String): Single<LoanConditions>
    fun updateLoans(token: String): Single<List<Loan>>
    fun clearLoans(): Completable
}