package com.example.droid.loan.testable.data.converter

import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.data.model.*
import com.example.droid.loan.domain.entity.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataConverterTest {
    private val dataUserEntity = UserModel(
        name = "Max",
        role = "USER"
    )
    private val userEntity = User(
        name = "Max",
        role = Role.USER
    )
    private val dataAuth = AuthModel(
        name = "Max",
        password = "123"
    )
    private val auth = Auth(
        name = "Max",
        password = "123"
    )
    private val dataLoanConditions = LoanConditionsModel(
        period = 12,
        percent = 13.4,
        maxAmount = 20000
    )
    private val loanConditions =
        LoanConditions(
            period = 12,
            percent = 13.4,
            maxAmount = 20000
        )
    private val dataLoan = LoanModel(
        amount = 12000,
        date = "2020-12-05T08:33:11.370+00:00",
        firstName = "Max",
        id = 212,
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442",
        state = "REGISTERED"
    )
    val loan = Loan(
        amount = 12000,
        date = "2020-12-05T08:33:11.370+00:00",
        firstName = "Max",
        id = 212,
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442",
        state = State.REGISTERED
    )
    private val dataLoanRequest = LoanRequestModel(
        amount = 12000,
        firstName = "Max",
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442"
    )
    private val loanRequest = LoanRequest(
        amount = 12000,
        firstName = "Max",
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442"
    )
    private val dataConverter = DataConverter()

    @Test
    fun `from data user entity EXPECT user entity`() {

        val result = dataConverter.fromDataUserEntity(dataUserEntity)

        assertEquals(userEntity, result)
    }

    @Test
    fun `to data user entity EXPECT data user entity`() {

        val result = dataConverter.toDataUserEntity(userEntity)

        assertEquals(dataUserEntity, result)
    }

    @Test
    fun `from data auth EXPECT auth`() {

        val result = dataConverter.fromDataAuth(dataAuth)

        assertEquals(auth, result)
    }

    @Test
    fun `to data auth EXPECT data auth`() {

        val result = dataConverter.toDataAuth(auth)

        assertEquals(dataAuth, result)
    }

    @Test
    fun `from data loan conditions EXPECT loan conditions`() {

        val result = dataConverter.fromDataLoanConditions(dataLoanConditions)

        assertEquals(loanConditions, result)
    }

    @Test
    fun `to data loan conditions EXPECT data loan conditions`() {

        val result = dataConverter.toDataLoanConditions(loanConditions)

        assertEquals(dataLoanConditions, result)
    }

    @Test
    fun `from data loan EXPECT loan`() {

        val result = dataConverter.fromDataLoan(dataLoan)

        assertEquals(loan, result)
    }

    @Test
    fun `to data loan EXPECT data loan`() {

        val result = dataConverter.toDataLoan(loan)

        assertEquals(dataLoan, result)
    }

    @Test
    fun `from data loan request EXPECT loan request`() {

        val result = dataConverter.fromDataLoanRequest(dataLoanRequest)

        assertEquals(loanRequest, result)
    }

    @Test
    fun `to data loan request EXPECT data loan request`() {

        val result = dataConverter.toDataLoanRequest(loanRequest)

        assertEquals(dataLoanRequest, result)
    }
}