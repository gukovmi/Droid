package com.example.droid.loan.testable.domain.usecase

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.GetLoansUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetLoansUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var getLoansUseCase: GetLoansUseCase

    @Before
    fun onSetup() {
        getLoansUseCase =
            GetLoansUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke EXPECT single loans list`() {
        val token = "432423423243423423243"
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
        val loansList = listOf(loan)
        `when`(loanRepository.getLoans(token))
            .thenReturn(Single.just(loansList))

        val result = getLoansUseCase(token).test()

        result.assertValue(loansList)
    }
}