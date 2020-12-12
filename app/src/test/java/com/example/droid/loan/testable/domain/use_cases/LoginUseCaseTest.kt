package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.repositories.LoanRepository
import com.example.droid.loan.domain.use_cases.LoginUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onSetup() {
        loginUseCase =
            LoginUseCase(loanRepository)
    }

    @Test
    fun `on invoke EXPECT single token`() {
        val auth = Auth(
            name = "Max",
            password = "123321"
        )
        val token = "213213312"
        `when`(loanRepository.login(auth))
            .thenReturn(Single.just(token))

        val result = loginUseCase(auth).test()

        result.assertValue(token)
    }

}