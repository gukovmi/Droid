package com.example.droid.loan.testable.data.datasource

import android.content.SharedPreferences
import com.example.droid.loan.data.datasource.SharedPreferencesDataSource
import com.example.droid.loan.data.datasource.SharedPreferencesDataSourceImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesDataSourceTest {
    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var sharedPreferencesDataSource: SharedPreferencesDataSource
    private val token = "42344224342232442"
    private val name = "Max"
    private val language = "en"

    @Before
    fun onSetup() {
        sharedPreferencesDataSource =
            SharedPreferencesDataSourceImpl(
                sharedPreferences
            )
        `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    }

    @Test
    fun `write token EXPECT shared preferences editor write token`() {

        sharedPreferencesDataSource.writeToken(token)

        verify(sharedPreferencesEditor).putString("token", token)
    }

    @Test
    fun `write name EXPECT shared preferences editor write name`() {

        sharedPreferencesDataSource.writeName(name)

        verify(sharedPreferencesEditor).putString("name", name)
    }

    @Test
    fun `write instruction was shown EXPECT shared preferences editor write true`() {

        sharedPreferencesDataSource.writeInstructionWasShown()

        verify(sharedPreferencesEditor).putBoolean("instructionWasShown", true)
    }

    @Test
    fun `write language EXPECT shared preferences editor write language`() {

        sharedPreferencesDataSource.writeLanguage(language)

        verify(sharedPreferencesEditor).putString("language", language)
    }

    @Test
    fun `read token EXPECT token`() {
        `when`(sharedPreferences.getString("token", ""))
            .thenReturn(token)

        val result = sharedPreferencesDataSource.readToken()

        assertEquals(token, result)
    }

    @Test
    fun `read name EXPECT name`() {
        `when`(sharedPreferences.getString("name", ""))
            .thenReturn(name)

        val result = sharedPreferencesDataSource.readName()

        assertEquals(name, result)
    }

    @Test
    fun `read instruction was shown EXPECT true`() {
        `when`(sharedPreferences.getBoolean("instructionWasShown", false))
            .thenReturn(true)

        val result = sharedPreferencesDataSource.readInstructionWasShown()

        assertEquals(true, result)
    }

    @Test
    fun `read language EXPECT language`() {
        `when`(sharedPreferences.getString("language", ""))
            .thenReturn(language)

        val result = sharedPreferencesDataSource.readLanguage()

        assertEquals(language, result)
    }
}