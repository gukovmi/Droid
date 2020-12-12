package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.repositories.LoanRepository
import com.example.droid.loan.domain.use_cases.ClearLoansUseCase
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ClearLoansUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var clearLoansUseCase: ClearLoansUseCase

    @Before
    fun onSetup() {
        clearLoansUseCase =
            ClearLoansUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke EXPECT completable complete`() {
        `when`(loanRepository.clearLoans())
            .thenReturn(Completable.complete())

        val result = clearLoansUseCase().test()

        result.assertComplete()
    }
}