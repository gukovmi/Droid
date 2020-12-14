package com.example.droid.loan.data.datasource

import com.example.droid.loan.data.db.LoansDao
import com.example.droid.loan.data.model.DataLoan
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LocalLoanDataSource {
    fun getLoans(): Single<List<DataLoan>>
    fun getLoanById(id: Long): Single<DataLoan>
    fun saveLoans(loansList: List<DataLoan>): Completable
    fun clearLoans(): Completable
}

class LocalLoanDataSourceImpl @Inject constructor(private val db: LoansDao) : LocalLoanDataSource {
    override fun getLoans(): Single<List<DataLoan>> =
        db.getLoans()

    override fun getLoanById(id: Long): Single<DataLoan> =
        db.getLoanById(id)

    override fun saveLoans(loansList: List<DataLoan>): Completable =
        db.saveLoans(loansList)

    override fun clearLoans(): Completable =
        db.clearLoans()
}