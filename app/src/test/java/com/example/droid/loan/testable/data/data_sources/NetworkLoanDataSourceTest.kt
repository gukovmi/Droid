package com.example.droid.loan.testable.data.data_sources

import com.example.droid.loan.data.data_sources.NetworkLoanDataSource
import com.example.droid.loan.data.data_sources.NetworkLoanDataSourceImpl
import com.example.droid.loan.data.models.*
import com.example.droid.loan.data.network.LoanApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class NetworkLoanDataSourceTest {
    @Mock
    lateinit var loanApiClient: LoanApi
    private lateinit var networkLoanDataSource: NetworkLoanDataSource
    private val dataAuth = DataAuth("Max", "123")
    private val dataUserEntity = DataUserEntity("Max", "USER")
    private val token = "42344224342232442"
    private val dataLoanRequest = DataLoanRequest(
        amount = 12000,
        firstName = "Max",
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442"
    )
    private val dataLoan = DataLoan(
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
    fun `registration successful EXPECT single data user entity`() {
        `when`(loanApiClient.registration(dataAuth))
            .thenReturn(Single.just(dataUserEntity))

        val result = networkLoanDataSource.registration(dataAuth).test()

        result.assertValue(dataUserEntity)
    }

    @Test
    fun `registration with bad connection EXPECT single error`() {
        `when`(loanApiClient.registration(dataAuth))
            .thenReturn(Single.error(SocketTimeoutException()))

        val result = networkLoanDataSource.registration(dataAuth).test()

        result.assertError(SocketTimeoutException::class.java)
    }

    @Test
    fun `login successful EXPECT single token`() {
        `when`(loanApiClient.login(dataAuth))
            .thenReturn(Single.just(token))

        val result = networkLoanDataSource.login(dataAuth).test()

        result.assertValue(token)
    }

    @Test
    fun `login with bad connection EXPECT single error`() {
        `when`(loanApiClient.login(dataAuth))
            .thenReturn(Single.error(SocketTimeoutException()))

        val result = networkLoanDataSource.login(dataAuth).test()

        result.assertError(SocketTimeoutException::class.java)
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
            DataLoanConditions(
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