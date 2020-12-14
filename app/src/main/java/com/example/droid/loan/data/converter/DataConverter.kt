package com.example.droid.loan.data.converter

import com.example.droid.loan.data.model.*
import com.example.droid.loan.domain.entity.*

class DataConverter {
    fun fromDataUserEntity(dataUserEntity: DataUserEntity): UserEntity =
        UserEntity(
            name = dataUserEntity.name,
            role = toRole(dataUserEntity.role)
        )

    fun toDataUserEntity(userEntity: UserEntity): DataUserEntity =
        DataUserEntity(
            name = userEntity.name,
            role = fromRole(userEntity.role)
        )

    fun fromDataAuth(dataAuth: DataAuth): Auth =
        Auth(
            name = dataAuth.name,
            password = dataAuth.password
        )

    fun toDataAuth(auth: Auth): DataAuth =
        DataAuth(
            name = auth.name,
            password = auth.password
        )

    fun fromDataLoanConditions(dataLoanConditions: DataLoanConditions): LoanConditions =
        LoanConditions(
            period = dataLoanConditions.period,
            percent = dataLoanConditions.percent,
            maxAmount = dataLoanConditions.maxAmount
        )

    fun toDataLoanConditions(loanConditions: LoanConditions): DataLoanConditions =
        DataLoanConditions(
            period = loanConditions.period,
            percent = loanConditions.percent,
            maxAmount = loanConditions.maxAmount
        )

    fun fromDataLoan(dataLoan: DataLoan): Loan =
        Loan(
            percent = dataLoan.percent,
            period = dataLoan.period,
            state = toState(dataLoan.state),
            phoneNumber = dataLoan.phoneNumber,
            lastName = dataLoan.lastName,
            id = dataLoan.id,
            firstName = dataLoan.firstName,
            date = dataLoan.date,
            amount = dataLoan.amount
        )

    fun toDataLoan(loan: Loan): DataLoan =
        DataLoan(
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

    fun fromDataLoanRequest(dataLoanRequest: DataLoanRequest): LoanRequest =
        LoanRequest(
            percent = dataLoanRequest.percent,
            period = dataLoanRequest.period,
            amount = dataLoanRequest.amount,
            firstName = dataLoanRequest.firstName,
            lastName = dataLoanRequest.lastName,
            phoneNumber = dataLoanRequest.phoneNumber
        )

    fun toDataLoanRequest(loanRequest: LoanRequest): DataLoanRequest =
        DataLoanRequest(
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