package com.example.droid.loan.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.ReadLanguageUseCase
import com.example.droid.loan.domain.use_cases.WriteLanguageUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.ResultView
import javax.inject.Inject

interface ResultPresenter {
    fun setLanguage(language: String)
    fun showHelpDialog()
    fun returnToPreviousScreen()
}

class ResultPresenterImpl @Inject constructor(
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : ResultPresenter, BasePresenter<ResultView>() {
    override fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    override fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }

    override fun returnToPreviousScreen() {
        view?.navigateTo(R.id.action_resultFragment_to_personalAreaFragment)
    }
}