package com.example.droid.loan.testable.domain.usecase.info

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.info.WriteTokenUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WriteTokenUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository

    private lateinit var writeTokenUseCase: WriteTokenUseCase

    @Before
    fun onSetup() {
        writeTokenUseCase =
            WriteTokenUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT info repository write token`() {
        val token = "1323123121323213"

        writeTokenUseCase(token)

        verify(infoRepository).writeToken(token)
    }
}