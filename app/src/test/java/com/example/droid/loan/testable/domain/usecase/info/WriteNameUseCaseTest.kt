package com.example.droid.loan.testable.domain.usecase.info

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.info.WriteNameUseCase
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