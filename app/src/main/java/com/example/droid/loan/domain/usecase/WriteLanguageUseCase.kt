package com.example.droid.loan.domain.usecase

import com.example.droid.loan.domain.repository.InfoRepository
import javax.inject.Inject

class WriteLanguageUseCase @Inject constructor(private val infoRepository: InfoRepository) {
    operator fun invoke(language: String) {
        infoRepository.writeLanguage(language)
    }
}