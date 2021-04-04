package com.example.droid.loan.testable.presentation.presenter

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.usecase.*
import com.example.droid.loan.domain.usecase.auth.LoginUseCase
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.domain.usecase.info.WriteNameUseCase
import com.example.droid.loan.domain.usecase.info.WriteTokenUseCase
import com.example.droid.loan.presentation.presenter.LoginPresenterImpl
import com.example.droid.loan.testrule.SchedulersTestRule
import com.example.droid.loan.ui.fragment.LoginView
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var loginUseCase: LoginUseCase

    @Mock
    lateinit var writeTokenUseCase: WriteTokenUseCase

    @Mock
    lateinit var writeNameUseCase: WriteNameUseCase
    private val fieldsIsNotEmptyUseCase = FieldsIsNotEmptyUseCase()

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase

    @Mock
    lateinit var view: LoginView
    private lateinit var presenter: LoginPresenterImpl
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        presenter = LoginPresenterImpl(
            loginUseCase,
            writeTokenUseCase,
            writeNameUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `login with not empty fields successful EXPECT view navigate to with bundle`() {
        val name = "Max"
        val password = "123321"
        val auth = Auth(name, password)
        val token = "42344224342232442"
        `when`(loginUseCase(auth)).thenReturn(Single.just(token))

        presenter.login(name, password)

        verify(view).navigateToWithBundle(anyInt(), any(Bundle::class.java))
    }

    @Test
    fun `login with not empty fields successful EXPECT view start and then finish loading`() {
        val name = "Max"
        val password = "123321"
        val auth = Auth(name, password)
        val token = "42344224342232442"
        `when`(loginUseCase(auth)).thenReturn(Single.just(token))
        val orderVerifier = inOrder(view)

        presenter.login(name, password)

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `login with not empty fields at bad connection EXPECT view show error`() {
        val name = "Max"
        val password = "123321"
        val auth = Auth(name, password)
        `when`(loginUseCase(auth))
            .thenReturn(Single.error(SocketTimeoutException()))

        presenter.login(name, password)

        verify(view).showError(any(SocketTimeoutException::class.java))
    }

    @Test
    fun `login with not empty fields at bad connection EXPECT view start and then finish loading`() {
        val name = "Max"
        val password = "123321"
        val auth = Auth(name, password)
        `when`(loginUseCase(auth))
            .thenReturn(Single.error(SocketTimeoutException()))
        val orderVerifier = inOrder(view)

        presenter.login(name, password)

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `login with empty field EXPECT view show empty fields warning`() {
        val name = ""
        val password = "123321"

        presenter.login(name, password)

        verify(view).showEmptyFieldsWarning()
    }

    @Test
    fun `set other language EXPECT view recreate require activity`() {
        val language = "en"
        `when`(readLanguageUseCase.invoke())
            .thenReturn("ru")

        presenter.setLanguage(language)

        verify(view).recreateRequireActivity()
    }

    @Test
    fun `set current language EXPECT view no more interactions`() {
        val language = "ru"
        `when`(readLanguageUseCase.invoke())
            .thenReturn("ru")

        presenter.setLanguage(language)

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `return to previous screen EXPECT view navigate to previous screen`() {
        val actionId = R.id.action_loginFragment_to_registrationFragment

        presenter.returnToPreviousScreen()

        verify(view).navigateTo(actionId)
    }

    @Test
    fun `show help dialog EXPECT view navigate to help dialog`() {
        val helpDialogId = R.id.helpDialogFragment

        presenter.showHelpDialog()

        verify(view).navigateTo(helpDialogId)
    }
}