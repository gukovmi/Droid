package com.example.droid.loan.data.datasource

import com.example.droid.loan.data.db.LoansDao
import com.example.droid.loan.data.model.LoanModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LocalLoanDataSource {
    fun getLoans(): Single<List<LoanModel>>
    fun getLoanById(id: Long): Single<LoanModel>
    fun saveLoans(loansList: List<LoanModel>): Completable
    fun clearLoans(): Completable
}

class LocalLoanDataSourceImpl @Inject constructor(private val db: LoansDao) : LocalLoanDataSource {
    override fun getLoans(): Single<List<LoanModel>> =
        db.getLoans()

    override fun getLoanById(id: Long): Single<LoanModel> =
        db.getLoanById(id)

    override fun saveLoans(loansList: List<LoanModel>): Completable =
        db.saveLoans(loansList)

    override fun clearLoans(): Completable =
        db.clearLoans()
}