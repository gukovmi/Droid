package com.example.droid.loan.testable.data.datasource.auth

import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSource
import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSourceImpl
import com.example.droid.loan.data.model.AuthModel
import com.example.droid.loan.data.model.UserModel
import com.example.droid.loan.data.network.api.AuthApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class NetworkAuthDataSourceImplTest {
    @Mock
    lateinit var authApiClient: AuthApi
    private lateinit var networkAuthDataSource: NetworkAuthDataSource
    private val dataAuth = AuthModel("Max", "123")
    private val dataUserEntity = UserModel("Max", "USER")
    private val token = "42344224342232442"

    @Before
    fun onSetup() {
        networkAuthDataSource =
            NetworkAuthDataSourceImpl(
                authApiClient
            )
    }

    @Test
    fun `registration successful EXPECT single data user entity`() {
        `when`(authApiClient.registration(dataAuth))
            .thenReturn(Single.just(dataUserEntity))

        val result = networkAuthDataSource.registration(dataAuth).test()

        result.assertValue(dataUserEntity)
    }

    @Test
    fun `registration with bad connection EXPECT single error`() {
        `when`(authApiClient.registration(dataAuth))
            .thenReturn(Single.error(SocketTimeoutException()))

        val result = networkAuthDataSource.registration(dataAuth).test()

        result.assertError(SocketTimeoutException::class.java)
    }

    @Test
    fun `login successful EXPECT single token`() {
        `when`(authApiClient.login(dataAuth))
            .thenReturn(Single.just(token))

        val result = networkAuthDataSource.login(dataAuth).test()

        result.assertValue(token)
    }

    @Test
    fun `login with bad connection EXPECT single error`() {
        `when`(authApiClient.login(dataAuth))
            .thenReturn(Single.error(SocketTimeoutException()))

        val result = networkAuthDataSource.login(dataAuth).test()

        result.assertError(SocketTimeoutException::class.java)
    }
}