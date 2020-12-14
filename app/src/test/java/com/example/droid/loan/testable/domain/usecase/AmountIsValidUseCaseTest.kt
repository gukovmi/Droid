package com.example.droid.loan.testable.domain.usecase

import com.example.droid.loan.domain.usecase.AmountIsValidUseCase
import junit.framework.Assert.assertEquals
import org.junit.Test

class AmountIsValidUseCaseTest {
    private val amountIsValidUseCase =
        AmountIsValidUseCase()

    @Test
    fun `on invoke with amount more than maxAmount EXPECT false`() {
        val amount: Long = 12000
        val maxAmount: Long = 11000

        val result: Boolean = amountIsValidUseCase(amount, maxAmount)

        assertEquals(false, result)
    }

    @Test
    fun `on invoke with amount and maxAmount are equal EXPECT true`() {
        val amount: Long = 11000
        val maxAmount: Long = 11000

        val result: Boolean = amountIsValidUseCase(amount, maxAmount)

        assertEquals(true, result)
    }

    @Test
    fun `on invoke with amount less than maxAmount EXPECT true`() {
        val amount: Long = 10000
        val maxAmount: Long = 11000

        val result: Boolean = amountIsValidUseCase(amount, maxAmount)

        assertEquals(true, result)
    }
}