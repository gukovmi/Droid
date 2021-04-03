package com.example.droid.loan.testable.domain.usecase.auth

import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.repository.AuthRepository
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.auth.LoginUseCase
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
    lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onSetup() {
        loginUseCase =
            LoginUseCase(authRepository)
    }

    @Test
    fun `on invoke EXPECT single token`() {
        val auth = Auth(
            name = "Max",
            password = "123321"
        )
        val token = "213213312"
        `when`(authRepository.login(auth))
            .thenReturn(Single.just(token))

        val result = loginUseCase(auth).test()

        result.assertValue(token)
    }

}