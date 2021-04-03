package com.example.droid.loan.testable.domain.usecase.info

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReadLanguageUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var readLanguageUseCase: ReadLanguageUseCase

    @Before
    fun onSetup() {
        readLanguageUseCase =
            ReadLanguageUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT language`() {
        val language = "en"
        `when`(infoRepository.readLanguage())
            .thenReturn(language)

        val result = readLanguageUseCase()

        assertEquals(language, result)
    }
}