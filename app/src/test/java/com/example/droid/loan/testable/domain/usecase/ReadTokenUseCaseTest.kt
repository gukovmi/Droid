package com.example.droid.loan.testable.domain.usecase

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.ReadTokenUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ReadTokenUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var readTokenUseCase: ReadTokenUseCase

    @Before
    fun onSetup() {
        readTokenUseCase =
            ReadTokenUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT token`() {
        val token = "321321321321"
        `when`(infoRepository.readToken())
            .thenReturn(token)

        val result = readTokenUseCase()

        assertEquals(token, result)
    }
}