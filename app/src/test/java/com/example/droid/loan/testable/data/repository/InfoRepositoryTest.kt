package com.example.droid.loan.testable.data.repository

import com.example.droid.loan.data.datasource.SharedPreferencesDataSource
import com.example.droid.loan.data.repository.InfoRepositoryImpl
import com.example.droid.loan.domain.repository.InfoRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InfoRepositoryTest {
    @Mock
    lateinit var sharedPreferencesDataSource: SharedPreferencesDataSource
    private lateinit var infoRepository: InfoRepository
    private val token = "42344224342232442"
    private val name = "Max"
    private val language = "en"

    @Before
    fun onSetup() {
        infoRepository =
            InfoRepositoryImpl(
                sharedPreferencesDataSource
            )
    }

    @Test
    fun `write token EXCEPT shared preferences data source write token`() {

        infoRepository.writeToken(token)

        verify(sharedPreferencesDataSource).writeToken(token)
    }

    @Test
    fun `write name EXCEPT shared preferences data source write name`() {

        infoRepository.writeName(name)

        verify(sharedPreferencesDataSource).writeName(name)
    }

    @Test
    fun `write instruction was shown EXPECT shared preferences data source write instruction was shown`() {

        infoRepository.writeInstructionWasShown()

        verify(sharedPreferencesDataSource).writeInstructionWasShown()
    }

    @Test
    fun `write language EXCEPT shared preferences data source write language`() {

        infoRepository.writeLanguage(language)

        verify(sharedPreferencesDataSource).writeLanguage(language)
    }

    @Test
    fun `read token EXCEPT token`() {
        `when`(sharedPreferencesDataSource.readToken())
            .thenReturn(token)

        val result = infoRepository.readToken()

        assertEquals(token, result)
    }

    @Test
    fun `read name EXCEPT name`() {
        `when`(sharedPreferencesDataSource.readName())
            .thenReturn(name)

        val result = infoRepository.readName()

        assertEquals(name, result)
    }

    @Test
    fun `read instruction was shown EXPECT true`() {
        `when`(sharedPreferencesDataSource.readInstructionWasShown())
            .thenReturn(true)

        val result = infoRepository.readInstructionWasShown()

        assertEquals(true, result)
    }

    @Test
    fun `read language EXCEPT language`() {
        `when`(sharedPreferencesDataSource.readLanguage())
            .thenReturn(language)

        val result = infoRepository.readLanguage()

        assertEquals(language, result)
    }
}