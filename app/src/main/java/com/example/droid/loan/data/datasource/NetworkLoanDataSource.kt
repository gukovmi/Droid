package com.example.droid.loan.data.datasource

import com.example.droid.loan.data.model.*
import com.example.droid.loan.data.network.LoanApi
import io.reactivex.Single
import javax.inject.Inject


interface NetworkLoanDataSource {
    fun registration(auth: DataAuth): Single<DataUserEntity>
    fun login(auth: DataAuth): Single<String>
    fun getLoans(token: String): Single<List<DataLoan>>
    fun createLoan(token: String, loanRequest: DataLoanRequest): Single<DataLoan>
    fun getLoanById(token: String, id: Long): Single<DataLoan>
    fun getLoanConditions(token: String): Single<DataLoanConditions>
}

class NetworkLoanDataSourceImpl @Inject constructor(
    private val loanApiClient: LoanApi
) : NetworkLoanDataSource {
    override fun registration(auth: DataAuth): Single<DataUserEntity> =
        loanApiClient.registration(auth)

    override fun login(auth: DataAuth): Single<String> =
        loanApiClient.login(auth)

    override fun getLoans(token: String): Single<List<DataLoan>> =
        loanApiClient.getLoans(token)

    override fun createLoan(token: String, loanRequest: DataLoanRequest): Single<DataLoan> =
        loanApiClient.createLoan(token, loanRequest)

    override fun getLoanById(token: String, id: Long): Single<DataLoan> =
        loanApiClient.getLoanById(token, id)

    override fun getLoanConditions(token: String): Single<DataLoanConditions> =
        loanApiClient.getLoanConditions(token)
}