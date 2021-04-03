package com.example.droid.loan.testable.domain.usecase.loan

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.LoanRequest
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.loan.CreateLoanUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CreateLoanUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var createLoanUseCase: CreateLoanUseCase

    @Before
    fun onSetup() {
        createLoanUseCase =
            CreateLoanUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke EXPECT single loan`() {
        val token =
            "231321312321231321231"
        val amount: Long = 10000
        val firstName = "Max"
        val lastName = "Ivanov"
        val percent = 12.4
        val period = 23
        val phoneNumber = "88005553535"
        val loanRequest = LoanRequest(
            amount,
            firstName,
            lastName,
            percent,
            period,
            phoneNumber
        )
        val loan = Loan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            period = period,
            phoneNumber = phoneNumber,
            percent = percent,
            date = "", id = 12,
            state = State.REGISTERED
        )
        `when`(loanRepository.createLoan(token, loanRequest))
            .thenReturn(Single.just(loan))

        val result = createLoanUseCase(token, loanRequest).test()

        result.assertValue(loan)
    }

}