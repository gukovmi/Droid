package com.example.droid.loan.testable.data.datasource

import com.example.droid.loan.data.datasource.LocalLoanDataSource
import com.example.droid.loan.data.datasource.LocalLoanDataSourceImpl
import com.example.droid.loan.data.db.LoansDao
import com.example.droid.loan.data.model.LoanModel
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class LocalLoanDataSourceTest {
    @Mock
    lateinit var db: LoansDao
    private lateinit var localLoanDataSource: LocalLoanDataSource
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
        localLoanDataSource =
            LocalLoanDataSourceImpl(db)
    }

    @Test
    fun `get loans successful EXPECT single data loans list`() {
        `when`(db.getLoans())
            .thenReturn(Single.just(dataLoansList))

        val result = localLoanDataSource.getLoans().test()

        result.assertValue(dataLoansList)
    }

    @Test
    fun `get loans unsuccessful EXPECT single error`() {
        `when`(db.getLoans())
            .thenReturn(Single.error(IOException()))

        val result = localLoanDataSource.getLoans().test()

        result.assertError(IOException::class.java)
    }

    @Test
    fun `get loan by id successful EXPECT single data loan`() {
        `when`(db.getLoanById(id))
            .thenReturn(Single.just(dataLoan))

        val result = localLoanDataSource.getLoanById(id).test()

        result.assertValue(dataLoan)
    }

    @Test
    fun `get loan by id unsuccessful EXPECT single error`() {
        `when`(db.getLoanById(id))
            .thenReturn(Single.error(IOException()))

        val result = localLoanDataSource.getLoanById(id).test()

        result.assertError(IOException::class.java)
    }

    @Test
    fun `save loans list successful EXPECT completable complete`() {
        `when`(db.saveLoans(dataLoansList))
            .thenReturn(Completable.complete())

        val result = localLoanDataSource.saveLoans(dataLoansList).test()

        result.assertComplete()
    }

    @Test
    fun `save loans list unsuccessful EXPECT completable error`() {
        `when`(db.saveLoans(dataLoansList))
            .thenReturn(Completable.error(IOException()))

        val result = localLoanDataSource.saveLoans(dataLoansList).test()

        result.assertError(IOException::class.java)
    }

    @Test
    fun `clear loans successful EXPECT completable complete`() {
        `when`(db.clearLoans())
            .thenReturn(Completable.complete())

        val result = localLoanDataSource.clearLoans().test()

        result.assertComplete()
    }

    @Test
    fun `clear loans unsuccessful EXPECT completable error`() {
        `when`(db.clearLoans())
            .thenReturn(Completable.error(IOException()))

        val result = localLoanDataSource.clearLoans().test()

        result.assertError(IOException::class.java)
    }
}