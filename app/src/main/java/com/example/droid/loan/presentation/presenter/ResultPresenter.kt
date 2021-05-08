package com.example.droid.loan.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.ResultView
import javax.inject.Inject

class ResultPresenter @Inject constructor(
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : BasePresenter<ResultView>() {
    fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }

    fun returnToPreviousScreen() {
        view?.navigateTo(R.id.action_resultFragment_to_personalAreaFragment)
    }
}