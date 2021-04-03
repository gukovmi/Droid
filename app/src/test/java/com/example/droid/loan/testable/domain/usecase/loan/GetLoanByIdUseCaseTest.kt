package com.example.droid.loan.testable.domain.usecase.loan

import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.loan.GetLoanByIdUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLoanByIdUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var getLoanByIdUseCase: GetLoanByIdUseCase

    @Before
    fun onSetup() {
        getLoanByIdUseCase =
            GetLoanByIdUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke EXPECT single loan`() {
        val token =
            "321132321233122123"
        val id: Long = 32
        val loan = Loan(
            amount = 132312,
            firstName = "firstName",
            lastName = "lastName",
            period = 12,
            phoneNumber = "55654645",
            percent = 23.3,
            date = "", id = 12,
            state = State.REGISTERED
        )
        Mockito.`when`(loanRepository.getLoanById(token, id))
            .thenReturn(Single.just(loan))

        val result = getLoanByIdUseCase(token, id).test()

        result.assertValue(loan)
    }
}