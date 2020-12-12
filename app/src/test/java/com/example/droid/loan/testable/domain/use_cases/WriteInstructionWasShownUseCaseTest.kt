package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.repositories.InfoRepository
import com.example.droid.loan.domain.use_cases.WriteInstructionWasShownUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WriteInstructionWasShownUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var writeInstructionWasShownUseCase: WriteInstructionWasShownUseCase

    @Before
    fun onSetup() {
        writeInstructionWasShownUseCase =
            WriteInstructionWasShownUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT info repository write instruction was shown`() {

        writeInstructionWasShownUseCase()

        verify(infoRepository).writeInstructionWasShown()
    }
}