package com.example.droid.loan.domain.usecase.info

import com.example.droid.loan.domain.repository.InfoRepository
import javax.inject.Inject

class WriteTokenUseCase @Inject constructor(private val infoRepository: InfoRepository) {
    operator fun invoke(token: String) {
        infoRepository.writeToken(token)
    }
}