package com.example.droid.loan.data.repository

import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.data.datasource.LocalLoanDataSource
import com.example.droid.loan.data.datasource.NetworkLoanDataSource
import com.example.droid.loan.domain.entity.*
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class LoanRepositoryImpl @Inject constructor(
    private val networkLoanDataSource: NetworkLoanDataSource,
    private val localLoanDataSource: LocalLoanDataSource,
    private val dataConverter: DataConverter
) : LoanRepository {
    override fun registration(auth: Auth): Single<UserEntity> =
        networkLoanDataSource.registration(dataConverter.toDataAuth(auth))
            .map {
                dataConverter.fromDataUserEntity(it)
            }

    override fun login(auth: Auth): Single<String> =
        networkLoanDataSource.login(dataConverter.toDataAuth(auth))

    override fun getLoans(token: String): Single<List<Loan>> =
        networkLoanDataSource.getLoans(token)
            .flatMap {
                localLoanDataSource.clearLoans()
                    .andThen(localLoanDataSource.saveLoans(it))
                    .andThen(Single.just(it))
            }
            .onErrorResumeNext {
                localLoanDataSource.getLoans()
            }
            .map { list ->
                list.map { dataConverter.fromDataLoan(it) }
            }

    override fun createLoan(token: String, loanRequest: LoanRequest): Single<Loan> =
        networkLoanDataSource.createLoan(token, dataConverter.toDataLoanRequest(loanRequest))
            .map {
                dataConverter.fromDataLoan(it)
            }

    override fun getLoanById(token: String, id: Long): Single<Loan> =
        networkLoanDataSource.getLoanById(token, id)
            .onErrorResumeNext {
                localLoanDataSource.getLoanById(id)
            }
            .map {
                dataConverter.fromDataLoan(it)
            }

    override fun getLoanConditions(token: String): Single<LoanConditions> =
        networkLoanDataSource.getLoanConditions(token)
            .map {
                dataConverter.fromDataLoanConditions(it)
            }

    override fun updateLoans(token: String): Single<List<Loan>> =
        networkLoanDataSource.getLoans(token)
            .flatMap {
                localLoanDataSource.clearLoans()
                    .andThen(localLoanDataSource.saveLoans(it))
                    .andThen(Single.just(it))
            }
            .map { list ->
                list.map { dataConverter.fromDataLoan(it) }
            }

    override fun clearLoans(): Completable =
        localLoanDataSource.clearLoans()
}