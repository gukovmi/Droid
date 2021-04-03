package com.example.droid.loan.data.datasource.loan

import com.example.droid.loan.data.model.LoanConditionsModel
import com.example.droid.loan.data.model.LoanModel
import com.example.droid.loan.data.model.LoanRequestModel
import com.example.droid.loan.data.network.api.LoanApi
import io.reactivex.Single
import javax.inject.Inject


interface NetworkLoanDataSource {
    fun getLoans(token: String): Single<List<LoanModel>>
    fun createLoan(token: String, loanRequestModel: LoanRequestModel): Single<LoanModel>
    fun getLoanById(token: String, id: Long): Single<LoanModel>
    fun getLoanConditions(token: String): Single<LoanConditionsModel>
}

class NetworkLoanDataSourceImpl @Inject constructor(
    private val loanApiClient: LoanApi
) : NetworkLoanDataSource {
    override fun getLoans(token: String): Single<List<LoanModel>> =
        loanApiClient.getLoans(token)

    override fun createLoan(token: String, loanRequestModel: LoanRequestModel): Single<LoanModel> =
        loanApiClient.createLoan(token, loanRequestModel)

    override fun getLoanById(token: String, id: Long): Single<LoanModel> =
        loanApiClient.getLoanById(token, id)

    override fun getLoanConditions(token: String): Single<LoanConditionsModel> =
        loanApiClient.getLoanConditions(token)
}