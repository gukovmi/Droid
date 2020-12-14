package com.example.droid.loan.data.datasource

import com.example.droid.loan.data.model.*
import com.example.droid.loan.data.network.LoanApi
import io.reactivex.Single
import javax.inject.Inject


interface NetworkLoanDataSource {
    fun registration(authModel: AuthModel): Single<UserModel>
    fun login(authModel: AuthModel): Single<String>
    fun getLoans(token: String): Single<List<LoanModel>>
    fun createLoan(token: String, loanRequestModel: LoanRequestModel): Single<LoanModel>
    fun getLoanById(token: String, id: Long): Single<LoanModel>
    fun getLoanConditions(token: String): Single<LoanConditionsModel>
}

class NetworkLoanDataSourceImpl @Inject constructor(
    private val loanApiClient: LoanApi
) : NetworkLoanDataSource {
    override fun registration(authModel: AuthModel): Single<UserModel> =
        loanApiClient.registration(authModel)

    override fun login(authModel: AuthModel): Single<String> =
        loanApiClient.login(authModel)

    override fun getLoans(token: String): Single<List<LoanModel>> =
        loanApiClient.getLoans(token)

    override fun createLoan(token: String, loanRequestModel: LoanRequestModel): Single<LoanModel> =
        loanApiClient.createLoan(token, loanRequestModel)

    override fun getLoanById(token: String, id: Long): Single<LoanModel> =
        loanApiClient.getLoanById(token, id)

    override fun getLoanConditions(token: String): Single<LoanConditionsModel> =
        loanApiClient.getLoanConditions(token)
}