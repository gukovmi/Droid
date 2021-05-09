package com.example.droid.loan.testable.presentation.presenter

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.LoanConditions
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.domain.usecase.info.*
import com.example.droid.loan.domain.usecase.loan.GetLoanConditionsUseCase
import com.example.droid.loan.domain.usecase.loan.GetLoansUseCase
import com.example.droid.loan.domain.usecase.loan.UpdateLoansUseCase
import com.example.droid.loan.presentation.presenter.PersonalAreaPresenter
import com.example.droid.loan.testrule.SchedulersTestRule
import com.example.droid.loan.ui.fragment.PersonalAreaView
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class PersonalAreaPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var getLoansUseCase: GetLoansUseCase

    @Mock
    lateinit var updateLoansUseCase: UpdateLoansUseCase

    @Mock
    lateinit var getLoansConditionsUseCase: GetLoanConditionsUseCase

    @Mock
    lateinit var readTokenUseCase: ReadTokenUseCase

    @Mock
    lateinit var readNameUseCase: ReadNameUseCase

    @Mock
    lateinit var readInstructionWasShownUseCase: ReadInstructionWasShownUseCase

    @Mock
    lateinit var writeInstructionWasShownUseCase: WriteInstructionWasShownUseCase

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase

    @Mock
    lateinit var view: PersonalAreaView
    private lateinit var presenter: PersonalAreaPresenter
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    private val token = "321321321321321"
    private val loan = Loan(
        amount = 12000,
        date = "2020-12-05T08:33:11.370+00:00",
        firstName = "Max",
        id = 212,
        lastName = "Ivanov",
        percent = 13.46,
        period = 12,
        phoneNumber = "44242442",
        state = State.REGISTERED
    )
    private val loanConditions =
        LoanConditions(
            period = 12,
            percent = 13.4,
            maxAmount = 20000
        )
    private val loansList = listOf(loan)

    @Before
    fun onSetup() {
        presenter = PersonalAreaPresenter(
            getLoansUseCase,
            updateLoansUseCase,
            getLoansConditionsUseCase,
            readTokenUseCase,
            readNameUseCase,
            readInstructionWasShownUseCase,
            writeInstructionWasShownUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `get loan conditions successful EXPECT view navigate to with bundle`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansConditionsUseCase.invoke(token))
            .thenReturn(Single.just(loanConditions))

        presenter.getLoanConditions()

        verify(view).navigateToWithBundle(anyInt(), any(Bundle::class.java))
    }

    @Test
    fun `get loan conditions successful EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansConditionsUseCase.invoke(token))
            .thenReturn(Single.just(loanConditions))
        val orderVerifier = inOrder(view)

        presenter.getLoanConditions()

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `get loan conditions with bad connection EXPECT view show error`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansConditionsUseCase.invoke(token))
            .thenReturn(Single.error(SocketTimeoutException()))

        presenter.getLoanConditions()

        verify(view).showError(any(SocketTimeoutException::class.java))
    }

    @Test
    fun `get loans successful EXPECT view show loans`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansUseCase.invoke(token))
            .thenReturn(Single.just(loansList))

        presenter.getLoans()

        verify(view).showLoans(loansList)
    }

    @Test
    fun `get loans successful EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansUseCase.invoke(token))
            .thenReturn(Single.just(loansList))
        val orderVerifier = inOrder(view)

        presenter.getLoans()

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `get loans unsuccessful EXPECT view show error`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansUseCase.invoke(token))
            .thenReturn(Single.error(IOException()))

        presenter.getLoans()

        verify(view).showError(any(IOException::class.java))
    }

    @Test
    fun `get loans unsuccessful EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoansUseCase.invoke(token))
            .thenReturn(Single.error(IOException()))
        val orderVerifier = inOrder(view)

        presenter.getLoans()

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `update loans with good connection EXPECT view show loans`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(updateLoansUseCase.invoke(token))
            .thenReturn(Single.just(loansList))

        presenter.updateLoans()

        verify(view).showLoans(loansList)
    }

    @Test
    fun `update loans with good connection EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(updateLoansUseCase.invoke(token))
            .thenReturn(Single.just(loansList))
        val orderVerifier = inOrder(view)

        presenter.updateLoans()

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `update loans with bad connection EXPECT view show error`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(updateLoansUseCase.invoke(token))
            .thenReturn(Single.error(SocketTimeoutException()))

        presenter.updateLoans()

        verify(view).showError(any(SocketTimeoutException::class.java))
    }

    @Test
    fun `update loans with bad connection EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(updateLoansUseCase.invoke(token))
            .thenReturn(Single.error(SocketTimeoutException()))
        val orderVerifier = inOrder(view)

        presenter.updateLoans()

        orderVerifier.verify(view).startLoading()
        orderVerifier.verify(view).finishLoading()
    }

    @Test
    fun `get name EXPECT name`() {
        val name = "Max"
        `when`(readNameUseCase.invoke())
            .thenReturn(name)

        val result = presenter.getName()

        assertEquals(name, result)
    }

    @Test
    fun `get first help first time EXPECT view navigate to help dialog`() {
        `when`(readInstructionWasShownUseCase.invoke())
            .thenReturn(false)

        presenter.getHelpFirstTime()

        verify(view).navigateTo(R.id.helpDialogFragment)
    }

    @Test
    fun `get first help not first time EXPECT view no more interactions`() {
        `when`(readInstructionWasShownUseCase.invoke())
            .thenReturn(true)

        presenter.getHelpFirstTime()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `get loan details EXPECT view navigate to with bundle`() {
        val id: Long = 38

        presenter.getLoanDetails(id)

        verify(view).navigateToWithBundle(anyInt(), any(Bundle::class.java))
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
    fun `logout EXPECT view navigate to logout dialog`() {
        val actionId = R.id.action_personalAreaFragment_to_logoutDialogFragment

        presenter.logout()

        verify(view).navigateTo(actionId)
    }

    @Test
    fun `show help dialog EXPECT view navigate to help dialog`() {
        val helpDialogId = R.id.helpDialogFragment

        presenter.showHelpDialog()

        verify(view).navigateTo(helpDialogId)
    }
}