package com.example.droid.loan.testable.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.entity.Role
import com.example.droid.loan.domain.entity.User
import com.example.droid.loan.domain.usecase.FieldsIsNotEmptyUseCase
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.auth.RegistrationUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.presentation.presenter.RegistrationPresenter
import com.example.droid.loan.testrule.SchedulersTestRule
import com.example.droid.loan.ui.fragment.RegistrationView
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
class RegistrationPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var registrationUseCase: RegistrationUseCase

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase
    private var fieldsIsNotEmptyUseCase = FieldsIsNotEmptyUseCase()

    @Mock
    lateinit var view: RegistrationView
    private lateinit var presenter: RegistrationPresenter
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        presenter = RegistrationPresenter(
            registrationUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `registration with not empty fields and good connection EXPECT view navigate to login fragment`() {
        val name = "Max"
        val password = "999666"
        val auth = Auth(name, password)
        val userEntity = User(name, Role.USER)
        val actionId = R.id.action_registrationFragment_to_loginFragment
        `when`(registrationUseCase.invoke(auth))
            .thenReturn(Single.just(userEntity))

        presenter.registration(name, password)

        verify(view).navigateTo(actionId)
    }

    @Test
    fun `registration with not empty fields and good connection EXPECT view start and then finish loading`() {
        val name = "Max"
        val password = "999666"
        val auth = Auth(name, password)
        val userEntity = User(name, Role.USER)
        `when`(registrationUseCase.invoke(auth))
            .thenReturn(Single.just(userEntity))
        val orderVerifier = inOrder(view)

        presenter.registration(name, password)

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `registration with not empty fields and bad connection EXPECT view show error`() {
        val name = "Max"
        val password = "999666"
        val auth = Auth(name, password)
        `when`(registrationUseCase.invoke(auth))
            .thenReturn(Single.error(SocketTimeoutException()))

        presenter.registration(name, password)

        verify(view).showError(any(SocketTimeoutException::class.java))
    }

    @Test
    fun `registration with not empty fields and bad connection EXPECT view start and then finish loading`() {
        val name = "Max"
        val password = "999666"
        val auth = Auth(name, password)
        `when`(registrationUseCase.invoke(auth))
            .thenReturn(Single.error(SocketTimeoutException()))
        val orderVerifier = inOrder(view)

        presenter.registration(name, password)

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `registration with empty field EXPECT view show empty fields warning`() {
        val name = "Max"
        val password = ""

        presenter.registration(name, password)

        verify(view).showEmptyFieldsWarning()
    }

    @Test
    fun `skip registration EXPECT view navigate to login fragment`() {
        val actonId = R.id.action_registrationFragment_to_loginFragment

        presenter.skipRegistration()

        verify(view).navigateTo(actonId)
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
    fun `show help dialog EXPECT view navigate to help dialog`() {
        val helpDialogId = R.id.helpDialogFragment

        presenter.showHelpDialog()

        verify(view).navigateTo(helpDialogId)
    }
}