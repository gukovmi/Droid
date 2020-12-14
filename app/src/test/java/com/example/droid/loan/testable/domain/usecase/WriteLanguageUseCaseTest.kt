package com.example.droid.loan.testable.domain.usecase

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.WriteLanguageUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WriteLanguageUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var writeLanguageUseCase: WriteLanguageUseCase

    @Before
    fun onSetup() {
        writeLanguageUseCase =
            WriteLanguageUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT info repository write language`() {
        val language = "en"

        writeLanguageUseCase(language)

        verify(infoRepository).writeLanguage(language)
    }
}