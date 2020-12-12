package com.example.droid.loan.testable.domain.use_cases

import com.example.droid.loan.domain.use_cases.FieldsIsNotEmptyUseCase
import junit.framework.Assert.assertEquals
import org.junit.Test

class FieldsIsNotEmptyUseCaseTest {
    private val fieldsIsNotEmptyUseCase =
        FieldsIsNotEmptyUseCase()

    @Test
    fun `on invoke with empty field EXPECT false`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val amount = ""
        val phoneNumber = "6646565"
        val list = listOf(firstName, lastName, amount, phoneNumber)

        val result: Boolean = fieldsIsNotEmptyUseCase(list)

        assertEquals(false, result)
    }

    @Test
    fun `on invoke without empty fields EXPECT true`() {
        val firstName = "Max"
        val lastName = "Ivanov"
        val amount = "3333"
        val phoneNumber = "6646565"
        val list = listOf(firstName, lastName, amount, phoneNumber)

        val result: Boolean = fieldsIsNotEmptyUseCase(list)

        assertEquals(true, result)
    }
}