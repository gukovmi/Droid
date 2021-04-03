package com.example.droid.loan.testable.data.repository.auth

import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSource
import com.example.droid.loan.data.datasource.loan.LocalLoanDataSource
import com.example.droid.loan.data.datasource.loan.NetworkLoanDataSource
import com.example.droid.loan.data.model.AuthModel
import com.example.droid.loan.data.model.LoanModel
import com.example.droid.loan.data.model.UserModel
import com.example.droid.loan.data.repository.auth.AuthRepositoryImpl
import com.example.droid.loan.data.repository.loan.LoanRepositoryImpl
import com.example.droid.loan.domain.entity.*
import com.example.droid.loan.domain.repository.AuthRepository
import com.example.droid.loan.domain.repository.LoanRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {
    @Mock
    lateinit var networkAuthDataSource: NetworkAuthDataSource

    private val dataConverter =
        DataConverter()
    private lateinit var authRepository: AuthRepository
    private val auth = Auth(
        name = "Max",
        password = "123"
    )
    private val token = "42344224342232442"
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        authRepository =
            AuthRepositoryImpl(
                networkAuthDataSource,
                dataConverter
            )
    }

    @Test
    fun `registration successful EXPECT single user entity`() {
        val dataUserEntity = UserModel(
            name = "Max",
            role = "USER"
        )
        val userEntity = User(
            name = "Max",
            role = Role.USER
        )
        `when`(networkAuthDataSource.registration(any(AuthModel::class.java)))
            .thenReturn(Single.just(dataUserEntity))

        val result = authRepository.registration(auth).test()

        result.assertValue(userEntity)
    }

    @Test
    fun `login successful EXPECT single token`() {
        `when`(networkAuthDataSource.login(any(AuthModel::class.java)))
            .thenReturn(Single.just(token))

        val result = authRepository.login(auth).test()

        result.assertValue(token)
    }
}