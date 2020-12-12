package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.repositories.InfoRepository
import com.example.droid.loan.domain.use_cases.WriteLanguageUseCase
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