package com.example.droid.loan.testable.presentation.presenters

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entities.Loan
import com.example.droid.loan.domain.entities.LoanConditions
import com.example.droid.loan.domain.entities.LoanRequest
import com.example.droid.loan.domain.entities.State
import com.example.droid.loan.domain.use_cases.*
import com.example.droid.loan.presentation.presenters.CreateLoanPresenterImpl
import com.example.droid.loan.test_rules.SchedulersTestRule
import com.example.droid.loan.ui.fragments.CreateLoanView
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
class CreateLoanPresenterTest {
    @JvmField
    @Rule
    val testRule = SchedulersTestRule()

    @Mock
    lateinit var readTokenUseCase: ReadTokenUseCase

    @Mock
    lateinit var createLoanUseCase: CreateLoanUseCase

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase
    private val amountIsValidUseCase = AmountIsValidUseCase()
    private val fieldsIsNotEmptyUseCase = FieldsIsNotEmptyUseCase()

    @Mock
    lateinit var view: CreateLoanView
    private lateinit var presenter: CreateLoanPresenterImpl
    private val loanConditions =
        LoanConditions(
            period = 12,
            percent = 13.4,
            maxAmount = 20000
        )
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
    val token = "321312321321321"

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Before
    fun onSetup() {
        presenter = CreateLoanPresenterImpl(
            readTokenUseCase,
            createLoanUseCase,
            amountIsValidUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `create loan with not empty fields and valid amount and good connection EXPECT view navigate to with bundle`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "3333"
        val loanRequest = LoanRequest(
            phoneNumber = phoneNumber,
            lastName = lastName,
            firstName = firstName,
            amount = amount.toLong(),
            percent = loanConditions.percent,
            period = loanConditions.period
        )
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(createLoanUseCase.invoke(token, loanRequest))
            .thenReturn(Single.just(loan))

        presenter.setLoanConditions(loanConditions)
        presenter.showLoanConditions()
        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        verify(view).navigateToWithBundle(anyInt(), any(Bundle::class.java))
    }

    @Test
    fun `create loan with not empty fields and valid amount and good connection EXPECT view start and then finish loading`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "3333"
        val loanRequest = LoanRequest(
            phoneNumber = phoneNumber,
            lastName = lastName,
            firstName = firstName,
            amount = amount.toLong(),
            percent = loanConditions.percent,
            period = loanConditions.period
        )
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(createLoanUseCase.invoke(token, loanRequest))
            .thenReturn(Single.just(loan))
        val orderVerifier = inOrder(view)

        presenter.setLoanConditions(loanConditions)
        presenter.showLoanConditions()
        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        orderVerifier.verify(view).startCreateLoanLoading()
        orderVerifier.verify(view).finishCreateLoanLoading()
    }

    @Test
    fun `create loan with not empty fields and valid amount and bad connection EXPECT view start and then finish loading`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "3333"
        val loanRequest = LoanRequest(
            phoneNumber = phoneNumber,
            lastName = lastName,
            firstName = firstName,
            amount = amount.toLong(),
            percent = loanConditions.percent,
            period = loanConditions.period
        )
        `when`(readTokenUseCase.invoke())
            .thenReturn(token)
        `when`(createLoanUseCase.invoke(token, loanRequest))
            .thenReturn(Single.error(SocketTimeoutException()))
        val orderVerifier = inOrder(view)

        presenter.setLoanConditions(loanConditions)
        presenter.showLoanConditions()
        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        orderVerifier.verify(view).startCreateLoanLoading()
        orderVerifier.verify(view).finishCreateLoanLoading()
    }


    @Test
    fun `create loan with empty field EXPECT view show empty fields warning`() {
        val firstName = ""
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "3333"

        presenter.setLoanConditions(loanConditions)
        presenter.showLoanConditions()
        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        verify(view).showEmptyFieldsWarning()
    }

    @Test
    fun `create loan with not valid amount EXPECT view show exceeding max amount warning`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "21000"

        presenter.setLoanConditions(loanConditions)
        presenter.showLoanConditions()
        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        verify(view).showExceedingMaxAmountWarning()
    }

    @Test
    fun `create loan with not initialized loanConditions EXPECT view show is not initialized variable warning`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val phoneNumber = "2312312"
        val amount = "21000"

        presenter.createLoan(
            amount = amount,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )

        verify(view).showIsNotInitializedVariableWarning()
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
        val actionId = R.id.action_createLoanFragment_to_personalAreaFragment

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