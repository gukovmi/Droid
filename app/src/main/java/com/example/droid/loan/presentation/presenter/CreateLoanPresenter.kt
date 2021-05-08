package com.example.droid.loan.presentation.presenter

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entity.LoanConditions
import com.example.droid.loan.domain.entity.LoanRequest
import com.example.droid.loan.domain.usecase.AmountIsValidUseCase
import com.example.droid.loan.domain.usecase.FieldsIsNotEmptyUseCase
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.ReadTokenUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.domain.usecase.loan.CreateLoanUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.CreateLoanView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateLoanPresenter @Inject constructor(
    private val readTokenUseCase: ReadTokenUseCase,
    private val createLoanUseCase: CreateLoanUseCase,
    private val amountIsValidUseCase: AmountIsValidUseCase,
    private val fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : BasePresenter<CreateLoanView>() {
    private lateinit var loanConditions: LoanConditions

    fun setLoanConditions(loanConditions: LoanConditions) {
        this.loanConditions = loanConditions
    }

    fun showLoanConditions() {
        view?.showLoanConditions(loanConditions)
    }

    fun createLoan(
        amount: String,
        firstName: String,
        lastName: String,
        phoneNumber: String
    ) {
        if (this::loanConditions.isInitialized) {
            if (fieldsIsNotEmptyUseCase(listOf(amount, firstName, lastName, phoneNumber))) {
                if (amountIsValidUseCase(amount.toLong(), this.loanConditions.maxAmount))
                    sendLoan(amount.toLong(), firstName, lastName, phoneNumber)
                else view?.showExceedingMaxAmountWarning()
            } else view?.showEmptyFieldsWarning()
        } else view?.showIsNotInitializedVariableWarning()
    }

    private fun sendLoan(
        amount: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String
    ) {
        val loanRequest = LoanRequest(
            amount,
            firstName,
            lastName,
            this.loanConditions.percent,
            this.loanConditions.period,
            phoneNumber
        )
        view?.startLoading()
        createLoanUseCase(readTokenUseCase(), loanRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val bundle = Bundle().apply {
                    putParcelable("loan", it)
                }
                view?.finishLoading()
                view?.navigateToWithBundle(
                    R.id.action_createLoanFragment_to_resultFragment,
                    bundle
                )
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