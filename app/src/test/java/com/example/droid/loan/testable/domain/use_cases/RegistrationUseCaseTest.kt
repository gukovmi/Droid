package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.entities.Role
import com.example.droid.loan.domain.entities.UserEntity
import com.example.droid.loan.domain.repositories.LoanRepository
import com.example.droid.loan.domain.use_cases.RegistrationUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegistrationUseCaseTest {
    @Mock
    lateinit var loanRepository: LoanRepository
    private lateinit var registrationUseCase: RegistrationUseCase

    @Before
    fun onSetup() {
        registrationUseCase =
            RegistrationUseCase(
                loanRepository
            )
    }

    @Test
    fun `on invoke EXPECT single user entity`() {
        val auth = Auth(
            name = "Max",
            password = "123321"
        )
        val userEntity = UserEntity(
            name = "Max",
            role = Role.USER
        )
        `when`(loanRepository.registration(auth))
            .thenReturn(Single.just(userEntity))

        val result = registrationUseCase(auth).test()

        result.assertValue(userEntity)
    }
}