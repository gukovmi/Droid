package com.example.droid.loan.domain.usecase

import com.example.droid.loan.domain.repository.InfoRepository
import javax.inject.Inject

class WriteNameUseCase @Inject constructor(private val infoRepository: InfoRepository) {
    operator fun invoke(name: String) {
        infoRepository.writeName(name)
    }
}