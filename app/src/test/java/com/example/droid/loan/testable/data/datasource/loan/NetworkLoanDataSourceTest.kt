package com.example.droid.loan.testable.data.datasource.loan

import com.example.droid.loan.data.datasource.loan.NetworkLoanDataSource
import com.example.droid.loan.data.datasource.loan.NetworkLoanDataSourceImpl
import com.example.droid.loan.data.model.*
import com.example.droid.loan.data.network.api.LoanApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class NetworkLoanDataSourceTest {
    @Mock
    lateinit var loanApiClient: LoanApi
    private lateinit var networkLoanDataSource: NetworkLoanDataSource
    private val token = "42344224342232442"
    private val dataLoanRequest = LoanRequestModel(
        amount = 12000,
        firstName = "Max",
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442"
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
    private val dataLoansList = listOf(dataLoan)
    private val id = dataLoan.id

    @Before
    fun onSetup() {
        networkLoanDataSource =
            NetworkLoanDataSourceImpl(
                loanApiClient
            )
    }

    @Test
    fun `create loan successful EXPECT single data loan`() {
        `when`(loanApiClient.createLoan(token, dataLoanRequest))
            .thenReturn(Single.just(dataLoan))

        val result = networkLoanDataSource
            .createLoan(token, dataLoanRequest).test()

        result.assertValue(dataLoan)
    }

    @Test
    fun `create loan without connection EXPECT single error`() {
        `when`(loanApiClient.createLoan(token, dataLoanRequest))
            .thenReturn(Single.error(UnknownHostException()))

        val result = networkLoanDataSource
            .createLoan(token, dataLoanRequest).test()

        result.assertError(UnknownHostException::class.java)
    }

    @Test
    fun `get loans successful EXPECT single loans list`() {
        `when`(loanApiClient.getLoans(token))
            .thenReturn(Single.just(dataLoansList))

        val result = loanApiClient.getLoans(token).test()

        result.assertValue(dataLoansList)
    }

    @Test
    fun `get loan by id successful EXPECT single data loan`() {
        `when`(loanApiClient.getLoanById(token, id))
            .thenReturn(Single.just(dataLoan))

        val result = loanApiClient.getLoanById(token, id).test()

        result.assertValue(dataLoan)
    }

    @Test
    fun `get loan conditions successful EXPECT single loan conditions`() {
        val dataLoanConditions =
            LoanConditionsModel(
                period = 12,
                percent = 13.4,
                maxAmount = 20000
            )
        `when`(loanApiClient.getLoanConditions(token))
            .thenReturn(Single.just(dataLoanConditions))

        val result = loanApiClient.getLoanConditions(token).test()

        result.assertValue(dataLoanConditions)
    }
}