package com.example.droid.loan.data.datasource.info

import android.content.SharedPreferences
import javax.inject.Inject

interface SharedPreferencesDataSource {
    fun writeToken(token: String)
    fun writeName(name: String)
    fun writeInstructionWasShown()
    fun writeLanguage(language: String)
    fun readToken(): String
    fun readName(): String
    fun readInstructionWasShown(): Boolean
    fun readLanguage(): String
}

class SharedPreferencesDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) :
    SharedPreferencesDataSource {
    override fun writeToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("token", token)
            commit()
        }
    }

    override fun writeName(name: String) {
        with(sharedPreferences.edit()) {
            putString("name", name)
            commit()
        }
    }

    override fun writeInstructionWasShown() {
        with(sharedPreferences.edit()) {
            putBoolean("instructionWasShown", true)
            commit()
        }
    }

    override fun writeLanguage(language: String) {
        with(sharedPreferences.edit())
        {
            putString("language", language)
            commit()
        }
    }

    override fun readToken(): String =
        sharedPreferences.getString("token", "") ?: ""

    override fun readName(): String =
        sharedPreferences.getString("name", "") ?: ""

    override fun readInstructionWasShown(): Boolean =
        sharedPreferences.getBoolean("instructionWasShown", false)

    override fun readLanguage(): String =
        sharedPreferences.getString("language", "") ?: ""
}