package com.example.droid.loan.testable.data.repositories

import com.example.droid.loan.data.data_sources.LocalLoanDataSource
import com.example.droid.loan.data.data_sources.NetworkLoanDataSource
import com.example.droid.loan.data.models.*
import com.example.droid.loan.data.repositories.LoanRepositoryImpl
import com.example.droid.loan.domain.converters.DataConverter
import com.example.droid.loan.domain.entities.*
import com.example.droid.loan.domain.repositories.LoanRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException


@RunWith(MockitoJUnitRunner::class)
class LoanRepositoryTest {
    @Mock
    lateinit var networkLoanDataSource: NetworkLoanDataSource

    @Mock
    lateinit var localLoanDataSource: LocalLoanDataSource
    private val dataConverter =
        DataConverter()
    private lateinit var loanRepository: LoanRepository
    private val auth = Auth(
        name = "Max",
        password = "123"
    )
    private val token = "42344224342232442"
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
    private val loan = Loan(
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
    private val loanRequest = LoanRequest(
        amount = 12000,
        firstName = "Max",
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442"
    )
    private val id = loan.id
    private val dataLoansList = listOf(dataLoan)
    private val loansList = listOf(loan)
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        loanRepository =
            LoanRepositoryImpl(
                networkLoanDataSource,
                localLoanDataSource,
                dataConverter
            )
    }

    @Test
    fun `registration successful EXPECT single user entity`() {
        val dataUserEntity = DataUserEntity(
            name = "Max",
            role = "USER"
        )
        val userEntity = UserEntity(
            name = "Max",
            role = Role.USER
        )
        `when`(networkLoanDataSource.registration(any(DataAuth::class.java)))
            .thenReturn(Single.just(dataUserEntity))

        val result = loanRepository
            .registration(auth).test()

        result.assertValue(userEntity)
    }

    @Test
    fun `login successful EXPECT single token`() {
        `when`(networkLoanDataSource.login(any(DataAuth::class.java)))
            .thenReturn(Single.just(token))

        val result = loanRepository.login(auth).test()

        result.assertValue(token)
    }

    @Test
    fun `get loans at good connection EXPECT single loans list`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.just(dataLoansList))
        `when`(localLoanDataSource.clearLoans())
            .thenReturn(Completable.complete())
        `when`(localLoanDataSource.saveLoans(dataLoansList))
            .thenReturn(Completable.complete())

        val result = loanRepository.getLoans(token).test()

        result.assertValue(loansList)
    }

    @Test
    fun `get loans at good connection EXPECT local loan data source save loans list`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.just(dataLoansList))
        `when`(localLoanDataSource.clearLoans())
            .thenReturn(Completable.complete())
        `when`(localLoanDataSource.saveLoans(dataLoansList))
            .thenReturn(Completable.complete())

        loanRepository.getLoans(token).test()

        verify(localLoanDataSource).saveLoans(dataLoansList)
    }

    @Test
    fun `get loans at bad connection EXPECT single loans list`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.error<List<DataLoan>>(SocketTimeoutException()))
        `when`(localLoanDataSource.getLoans()).thenReturn(Single.just(dataLoansList))

        val result = loanRepository.getLoans(token).test()

        result.assertValue(loansList)
    }

    @Test
    fun `get loans at bad connection EXPECT local loan data source get loans`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.error<List<DataLoan>>(SocketTimeoutException()))
        `when`(localLoanDataSource.getLoans()).thenReturn(Single.just(dataLoansList))

        loanRepository.getLoans(token).test()

        verify(localLoanDataSource).getLoans()
    }

    @Test
    fun `create loan at good connection EXPECT single loan`() {
        `when`(networkLoanDataSource.createLoan(anyString(), any(DataLoanRequest::class.java)))
            .thenReturn(Single.just(dataLoan))

        val result = loanRepository.createLoan(token, loanRequest).test()

        result.assertValue(loan)
    }

    @Test
    fun `create loan at bad connection EXPECT exception`() {
        `when`(networkLoanDataSource.createLoan(anyString(), any(DataLoanRequest::class.java)))
            .thenReturn(Single.error<DataLoan>(SocketTimeoutException()))

        val result = loanRepository.createLoan(token, loanRequest).test()

        result.assertError(SocketTimeoutException::class.java)
    }

    @Test
    fun `get loan by id at good connection EXPECT single loan`() {
        `when`(networkLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.just(dataLoan))

        val result = loanRepository.getLoanById(token, id).test()

        result.assertValue(loan)
    }

    @Test
    fun `get loan by id at bad connection EXPECT single loan`() {
        `when`(networkLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error<DataLoan>(SocketTimeoutException()))
        `when`(localLoanDataSource.getLoanById(id))
            .thenReturn(Single.just(dataLoan))

        val result = loanRepository.getLoanById(token, id).test()

        result.assertValue(loan)
    }

    @Test
    fun `get loan by id at bad connection EXPECT local loan data source get loan by id`() {
        `when`(networkLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error<DataLoan>(SocketTimeoutException()))
        `when`(localLoanDataSource.getLoanById(id)).thenReturn(Single.just(dataLoan))

        loanRepository.getLoanById(token, id).test()

        verify(localLoanDataSource).getLoanById(id)
    }

    @Test
    fun `get loan conditions successful EXPECT single loan conditions`() {
        val dataLoanConditions = DataLoanConditions(
            period = 12,
            percent = 13.4,
            maxAmount = 20000
        )
        val loanConditions =
            LoanConditions(
                period = 12,
                percent = 13.4,
                maxAmount = 20000
            )
        `when`(networkLoanDataSource.getLoanConditions(token))
            .thenReturn(Single.just(dataLoanConditions))

        val result = loanRepository.getLoanConditions(token).test()

        result.assertValue(loanConditions)
    }

    @Test
    fun `update loans at good connection EXPECT single loans list`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.just(dataLoansList))
        `when`(localLoanDataSource.clearLoans())
            .thenReturn(Completable.complete())
        `when`(localLoanDataSource.saveLoans(dataLoansList))
            .thenReturn(Completable.complete())

        val result = loanRepository.updateLoans(token).test()

        result.assertValue(loansList)
    }

    @Test
    fun `update loans at good connection EXPECT local loan data source save loans`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.just(dataLoansList))
        `when`(localLoanDataSource.clearLoans())
            .thenReturn(Completable.complete())
        `when`(localLoanDataSource.saveLoans(dataLoansList))
            .thenReturn(Completable.complete())

        loanRepository.updateLoans(token).test()

        verify(localLoanDataSource).saveLoans(dataLoansList)
    }

    @Test
    fun `update loans at bad connection EXPECT exception`() {
        `when`(networkLoanDataSource.getLoans(token))
            .thenReturn(Single.error<List<DataLoan>>(SocketTimeoutException()))

        val result = loanRepository.updateLoans(token).test()

        result.assertError(SocketTimeoutException::class.java)
    }

    @Test
    fun `clear loans successful EXPECT completable complete`() {
        `when`(localLoanDataSource.clearLoans())
            .thenReturn(Completable.complete())

        val result = loanRepository.clearLoans().test()

        result.assertComplete()
    }
}