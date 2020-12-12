package com.example.droid.loan.domain.use_cases

import javax.inject.Inject

class FieldsIsNotEmptyUseCase @Inject constructor() {
    operator fun invoke(fields: List<String>): Boolean = fields.all { it.isNotEmpty() }
}