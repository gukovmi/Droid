package com.example.droid.loan.testable.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.entity.Loan
import com.example.droid.loan.domain.entity.State
import com.example.droid.loan.domain.usecase.loan.GetLoanByIdUseCase
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.ReadTokenUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.presentation.presenter.LoanDetailsPresenterImpl
import com.example.droid.loan.testrule.SchedulersTestRule
import com.example.droid.loan.ui.fragment.LoanDetailsView
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class LoanDetailsPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var readTokenUseCase: ReadTokenUseCase

    @Mock
    lateinit var getLoanByIdUseCase: GetLoanByIdUseCase

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase

    @Mock
    lateinit var view: LoanDetailsView
    private lateinit var presenter: LoanDetailsPresenterImpl
    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    private val token = "321312321321321"
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

    @Before
    fun onSetup() {
        presenter = LoanDetailsPresenterImpl(
            readTokenUseCase,
            getLoanByIdUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `get loan by id EXPECT view show loan details`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoanByIdUseCase.invoke(token, loan.id))
            .thenReturn(Single.just(loan))

        presenter.getLoanById(loan.id)

        verify(view).showLoanDetails(loan)
    }

    @Test
    fun `get loan by id EXPECT view start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoanByIdUseCase.invoke(token, loan.id))
            .thenReturn(Single.just(loan))
        val orderVerifier = inOrder(view)

        presenter.getLoanById(loan.id)

        orderVerifier.verify(view).startLoanLoading()
        orderVerifier.verify(view).finishLoanLoading()
    }

    @Test
    fun `get loan by id unsuccessful EXPECT view show error`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoanByIdUseCase.invoke(token, loan.id))
            .thenReturn(Single.error(IOException()))

        presenter.getLoanById(loan.id)

        verify(view).showError(any(IOException::class.java))
    }

    @Test
    fun `get loan by id unsuccessful EXPECT start and then finish loading`() {
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(getLoanByIdUseCase.invoke(token, loan.id))
            .thenReturn(Single.error(IOException()))
        val orderVerifier = inOrder(view)

        presenter.getLoanById(loan.id)

        orderVerifier.verify(view).startLoanLoading()
        orderVerifier.verify(view).finishLoanLoading()
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
        val actionId = R.id.action_loanDetailsFragment_to_personalAreaFragment

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