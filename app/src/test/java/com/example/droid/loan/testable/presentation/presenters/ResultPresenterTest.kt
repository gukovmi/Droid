package com.example.droid.loan.testable.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.ReadLanguageUseCase
import com.example.droid.loan.domain.use_cases.WriteLanguageUseCase
import com.example.droid.loan.presentation.presenters.ResultPresenterImpl
import com.example.droid.loan.ui.fragments.ResultView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ResultPresenterTest {
    @Mock
    lateinit var view: ResultView

    @Mock
    lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Mock
    lateinit var readLanguageUseCase: ReadLanguageUseCase
    private lateinit var presenter: ResultPresenterImpl

    @Before
    fun onSetup() {
        presenter = ResultPresenterImpl(
            writeLanguageUseCase,
            readLanguageUseCase
        )
        presenter.attachView(view)
    }

    @Test
    fun `set other language EXPECT view recreate require activity`() {
        val language = "en"
        Mockito.`when`(readLanguageUseCase.invoke())
            .thenReturn("ru")

        presenter.setLanguage(language)

        verify(view).recreateRequireActivity()
    }

    @Test
    fun `set current language EXPECT view no more interactions`() {
        val language = "ru"
        Mockito.`when`(readLanguageUseCase.invoke())
            .thenReturn("ru")

        presenter.setLanguage(language)

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `return to previous screen EXPECT view navigate to previous screen`() {
        val actionId = R.id.action_resultFragment_to_personalAreaFragment

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