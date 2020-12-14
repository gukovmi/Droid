package com.example.droid.loan.domain.repository

interface InfoRepository {
    fun writeToken(token: String)
    fun writeInstructionWasShown()
    fun writeName(name: String)
    fun writeLanguage(language: String)
    fun readToken(): String
    fun readName(): String
    fun readInstructionWasShown(): Boolean
    fun readLanguage(): String
}