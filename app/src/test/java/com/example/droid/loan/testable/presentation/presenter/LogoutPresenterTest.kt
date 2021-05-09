package com.example.droid.loan.testable.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.usecase.loan.ClearLoansUseCase
import com.example.droid.loan.domain.usecase.info.WriteTokenUseCase
import com.example.droid.loan.presentation.presenter.LogoutPresenter
import com.example.droid.loan.testrule.SchedulersTestRule
import com.example.droid.loan.ui.fragment.LogoutView
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class LogoutPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var clearLoansUseCase: ClearLoansUseCase

    @Mock
    lateinit var writeTokenUseCase: WriteTokenUseCase

    @Mock
    lateinit var view: LogoutView
    private lateinit var presenter: LogoutPresenter
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        presenter = LogoutPresenter(
            clearLoansUseCase,
            writeTokenUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `logout successfully EXPECT view navigate to registration fragment`() {
        val actionId = R.id.action_logoutDialogFragment_to_registrationFragment
        `when`(clearLoansUseCase.invoke())
            .thenReturn(Completable.complete())

        presenter.logout()

        verify(view).navigateTo(actionId)
    }

    @Test
    fun `logout unsuccessfully EXPECT view show error`() {
        `when`(clearLoansUseCase.invoke())
            .thenReturn(Completable.error(IOException()))

        presenter.logout()

        verify(view).showError(any(IOException::class.java))
    }

    @Test
    fun `cancel EXPECT view return to previous screen`() {

        presenter.cancel()

        verify(view).returnToPreviousScreen()
    }

}