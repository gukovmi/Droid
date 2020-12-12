package com.example.droid.loan.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.GetLoanByIdUseCase
import com.example.droid.loan.domain.use_cases.ReadLanguageUseCase
import com.example.droid.loan.domain.use_cases.ReadTokenUseCase
import com.example.droid.loan.domain.use_cases.WriteLanguageUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.LoanDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface LoanDetailsPresenter {
    fun getLoanById(id: Long)
    fun setLanguage(language: String)
    fun returnToPreviousScreen()
    fun showHelpDialog()
}

class LoanDetailsPresenterImpl @Inject constructor(
    private val readTokenUseCase: ReadTokenUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : LoanDetailsPresenter,
    BasePresenter<LoanDetailsView>() {
    override fun getLoanById(id: Long) {
        view?.startLoanLoading()
        getLoanByIdUseCase(readTokenUseCase(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loan ->
                view?.finishLoanLoading()
                view?.showLoanDetails(loan)
            }, {
                view?.finishLoanLoading()
                view?.showError(it)
            }).untilDestroy()
    }

    override fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    override fun returnToPreviousScreen() {
        view?.navigateTo(R.id.action_loanDetailsFragment_to_personalAreaFragment)
    }

    override fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }
}