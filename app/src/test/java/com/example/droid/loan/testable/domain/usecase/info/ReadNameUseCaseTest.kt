package com.example.droid.loan.testable.domain.usecase.info

import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.usecase.info.ReadNameUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReadNameUseCaseTest {
    @Mock
    lateinit var infoRepository: InfoRepository
    private lateinit var readNameUseCase: ReadNameUseCase

    @Before
    fun onSetup() {
        readNameUseCase =
            ReadNameUseCase(
                infoRepository
            )
    }

    @Test
    fun `on invoke EXPECT name`() {
        val name = "Max"
        `when`(infoRepository.readName())
            .thenReturn(name)

        val result = readNameUseCase()

        assertEquals(name, result)
    }
}