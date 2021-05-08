package com.example.droid.loan.presentation.presenter

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.usecase.info.*
import com.example.droid.loan.domain.usecase.loan.GetLoanConditionsUseCase
import com.example.droid.loan.domain.usecase.loan.GetLoansUseCase
import com.example.droid.loan.domain.usecase.loan.UpdateLoansUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.PersonalAreaView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonalAreaPresenter @Inject constructor(
    private val getLoansUseCase: GetLoansUseCase,
    private val updateLoansUseCase: UpdateLoansUseCase,
    private val getLoansConditionsUseCase: GetLoanConditionsUseCase,
    private val readTokenUseCase: ReadTokenUseCase,
    private val readNameUseCase: ReadNameUseCase,
    private val readInstructionWasShownUseCase: ReadInstructionWasShownUseCase,
    private val writeInstructionWasShownUseCase: WriteInstructionWasShownUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : BasePresenter<PersonalAreaView>() {
    fun getLoanConditions() {
        view?.startLoading()
        getLoansConditionsUseCase(readTokenUseCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val bundle: Bundle = Bundle().apply {
                    putParcelable(
                        "loanConditions",
                        it
                    )
                }
                view?.finishLoading()
                view?.navigateToWithBundle(
                    R.id.action_personalAreaFragment_to_createLoanFragment,
                    bundle
                )
            }, {
                view?.finishLoading()
                view?.showError(it)
            }).untilDestroy()
    }

    fun getLoans() {
        view?.startLoading()
        getLoansUseCase(readTokenUseCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loansList ->
                view?.finishLoading()
                view?.showLoans(loansList)
            }, {
                view?.finishLoading()
                view?.showError(it)
            }).untilDestroy()
    }

    fun updateLoans() {
        view?.startLoading()
        updateLoansUseCase(readTokenUseCase())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.finishLoading()
                view?.showLoans(it)
            }, {
                view?.finishLoading()
                view?.showError(it)
            }).untilDestroy()
    }

    fun getName(): String =
        readNameUseCase()

    fun getHelpFirstTime() {
        if (!readInstructionWasShownUseCase()) {
            writeInstructionWasShownUseCase()
            view?.navigateTo(R.id.helpDialogFragment)
        }
    }

    fun getLoanDetails(id: Long) {
        val bundle: Bundle = Bundle().apply {
            putLong(
                "loanId",
                id
            )
        }
        view?.navigateToWithBundle(
            R.id.action_personalAreaFragment_to_loanDetailsFragment,
            bundle
        )
    }

    fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    fun logout() {
        view?.navigateTo(R.id.action_personalAreaFragment_to_logoutDialogFragment)
    }

    fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }
}