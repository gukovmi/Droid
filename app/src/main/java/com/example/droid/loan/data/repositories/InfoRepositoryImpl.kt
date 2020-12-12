package com.example.droid.loan.data.repositories

import com.example.droid.loan.data.data_sources.SharedPreferencesDataSource
import com.example.droid.loan.domain.repositories.InfoRepository
import javax.inject.Inject

class InfoRepositoryImpl @Inject constructor(private val sharedPreferencesDataSource: SharedPreferencesDataSource) :
    InfoRepository {
    override fun writeToken(token: String) {
        sharedPreferencesDataSource.writeToken(token)
    }

    override fun writeInstructionWasShown() {
        sharedPreferencesDataSource.writeInstructionWasShown()
    }

    override fun writeName(name: String) {
        sharedPreferencesDataSource.writeName(name)
    }

    override fun writeLanguage(language: String) {
        sharedPreferencesDataSource.writeLanguage(language)
    }

    override fun readToken(): String =
        sharedPreferencesDataSource.readToken()

    override fun readName(): String =
        sharedPreferencesDataSource.readName()

    override fun readInstructionWasShown(): Boolean =
        sharedPreferencesDataSource.readInstructionWasShown()

    override fun readLanguage(): String =
        sharedPreferencesDataSource.readLanguage()
}