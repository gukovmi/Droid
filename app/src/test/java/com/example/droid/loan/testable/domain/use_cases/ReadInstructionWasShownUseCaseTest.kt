package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.repositories.InfoRepository
import com.example.droid.loan.domain.use_cases.ReadInstructionWasShownUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ReadInstructionWasShownUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var readInstructionWasShownUseCase: ReadInstructionWasShownUseCase

    @Before
    fun onSetup() {
        readInstructionWasShownUseCase =
            ReadInstructionWasShownUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT true`() {
        `when`(infoRepository.readInstructionWasShown())
            .thenReturn(true)

        val result = readInstructionWasShownUseCase()

        assertEquals(true, result)
    }
}