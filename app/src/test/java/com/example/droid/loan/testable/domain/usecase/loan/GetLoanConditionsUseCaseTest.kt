package com.example.droid.loan.testable.domain.usecase.loan

import com.example.droid.loan.domain.entity.LoanConditions
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.loan.GetLoanConditionsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLoanConditionsUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var getLoanConditionsUseCase: GetLoanConditionsUseCase

    @Before
    fun onSetup() {
        getLoanConditionsUseCase =
            GetLoanConditionsUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke with token EXPECT single loan conditions`() {
        val token = "342324432423342432"
        val loanConditions =
            LoanConditions(
                period = 12,
                percent = 13.4,
                maxAmount = 20000
            )
        `when`(loanRepository.getLoanConditions(token))
            .thenReturn(Single.just(loanConditions))

        val result = getLoanConditionsUseCase(token).test()

        result.assertValue(loanConditions)
    }
}