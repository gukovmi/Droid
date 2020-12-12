package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.repositories.InfoRepository
import com.example.droid.loan.domain.use_cases.WriteNameUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WriteNameUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var writeNameUseCase: WriteNameUseCase

    @Before
    fun onSetup() {
        writeNameUseCase =
            WriteNameUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT info repository write name`() {
        val name = "Max"

        writeNameUseCase(name)

        verify(infoRepository).writeName(name)
    }
}