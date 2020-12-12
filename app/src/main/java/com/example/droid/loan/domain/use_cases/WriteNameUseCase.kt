package com.example.droid.loan.domain.use_cases

import com.example.droid.loan.domain.repositories.InfoRepository
import javax.inject.Inject

class WriteNameUseCase @Inject constructor(private val infoRepository: InfoRepository) {
    operator fun invoke(name: String) {
        infoRepository.writeName(name)
    }
}