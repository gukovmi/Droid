package com.example.droid.loan.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.ReadTokenUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.domain.usecase.loan.GetLoanByIdUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.LoanDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanDetailsPresenter @Inject constructor(
    private val readTokenUseCase: ReadTokenUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : BasePresenter<LoanDetailsView>() {
    fun getLoanById(id: Long) {
        view?.startLoading()
        getLoanByIdUseCase(readTokenUseCase(), id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loan ->
                view?.finishLoading()
                view?.showLoanDetails(loan)
            }, {
                view?.finishLoading()
                view?.showError(it)
            }).untilDestroy()
    }

    fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    fun returnToPreviousScreen() {
        view?.navigateBack()
    }

    fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }
}