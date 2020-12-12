package com.example.droid.loan.testable.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.ReadTokenUseCase
import com.example.droid.loan.presentation.presenters.StartPresenterImpl
import com.example.droid.loan.ui.fragments.StartView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StartPresenterTest {
    @Mock
    lateinit var readTokenUseCase: ReadTokenUseCase

    @Mock
    lateinit var view: StartView
    private lateinit var presenter: StartPresenterImpl

    @Before
    fun onSetup() {
        presenter =
            StartPresenterImpl(
                readTokenUseCase
            )
    }

    @Test
    fun `on attach view with not empty token EXPECT view navigate to personal area fragment`() {
        val token = "123321312312321"
        `when`(readTokenUseCase()).thenReturn(token)

        presenter.attachView(view)

        verify(view).navigateTo(R.id.action_startFragment_to_personalAreaFragment)
    }

    @Test
    fun `on attach view with empty token EXPECT view navigate to registration fragment`() {
        val token = ""
        `when`(readTokenUseCase()).thenReturn(token)

        presenter.attachView(view)

        verify(view).navigateTo(R.id.action_startFragment_to_registrationFragment)
    }

}