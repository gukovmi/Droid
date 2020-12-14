package com.example.droid.loan.domain.usecase

import javax.inject.Inject

class AmountIsValidUseCase @Inject constructor() {
    operator fun invoke(amount: Long, maxAmount: Long): Boolean = (amount <= maxAmount)
}