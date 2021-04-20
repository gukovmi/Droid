package com.example.droid.loan.data.converter

import com.example.droid.loan.data.model.*
import com.example.droid.loan.domain.entity.*
import javax.inject.Inject

class DataConverter @Inject constructor() {
    fun fromDataUserEntity(userModel: UserModel): User =
        User(
            name = userModel.name,
            role = toRole(userModel.role)
        )

    fun toDataUserEntity(user: User): UserModel =
        UserModel(
            name = user.name,
            role = fromRole(user.role)
        )

    fun fromDataAuth(authModel: AuthModel): Auth =
        Auth(
            name = authModel.name,
            password = authModel.password
        )

    fun toDataAuth(auth: Auth): AuthModel =
        AuthModel(
            name = auth.name,
            password = auth.password
        )

    fun fromDataLoanConditions(loanConditionsModel: LoanConditionsModel): LoanConditions =
        LoanConditions(
            period = loanConditionsModel.period,
            percent = loanConditionsModel.percent,
            maxAmount = loanConditionsModel.maxAmount
        )

    fun toDataLoanConditions(loanConditions: LoanConditions): LoanConditionsModel =
        LoanConditionsModel(
            period = loanConditions.period,
            percent = loanConditions.percent,
            maxAmount = loanConditions.maxAmount
        )

    fun fromDataLoan(loanModel: LoanModel): Loan =
        Loan(
            percent = loanModel.percent,
            period = loanModel.period,
            state = toState(loanModel.state),
            phoneNumber = loanModel.phoneNumber,
            lastName = loanModel.lastName,
            id = loanModel.id,
            firstName = loanModel.firstName,
            date = loanModel.date,
            amount = loanModel.amount
        )

    fun toDataLoan(loan: Loan): LoanModel =
        LoanModel(
            percent = loan.percent,
            period = loan.period,
            state = fromState(loan.state),
            phoneNumber = loan.phoneNumber,
            lastName = loan.lastName,
            id = loan.id,
            firstName = loan.firstName,
            date = loan.date,
            amount = loan.amount
        )

    fun fromDataLoanRequest(loanRequestModel: LoanRequestModel): LoanRequest =
        LoanRequest(
            percent = loanRequestModel.percent,
            period = loanRequestModel.period,
            amount = loanRequestModel.amount,
            firstName = loanRequestModel.firstName,
            lastName = loanRequestModel.lastName,
            phoneNumber = loanRequestModel.phoneNumber
        )

    fun toDataLoanRequest(loanRequest: LoanRequest): LoanRequestModel =
        LoanRequestModel(
            percent = loanRequest.percent,
            period = loanRequest.period,
            amount = loanRequest.amount,
            firstName = loanRequest.firstName,
            lastName = loanRequest.lastName,
            phoneNumber = loanRequest.phoneNumber
        )

    private fun fromRole(value: Role): String {
        return value.name
    }

    private fun toRole(value: String): Role {
        return when (value) {
            Role.ADMIN.name -> Role.ADMIN
            else -> Role.USER
        }
    }

    private fun fromState(value: State): String {
        return value.name
    }

    private fun toState(value: String): State {
        return when (value) {
            State.APPROVED.name -> State.APPROVED
            State.REGISTERED.name -> State.REGISTERED
            else -> State.REJECTED
        }
    }
}