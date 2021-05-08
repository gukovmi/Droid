package com.example.droid.loan.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.usecase.info.WriteTokenUseCase
import com.example.droid.loan.domain.usecase.loan.ClearLoansUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.LogoutView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LogoutPresenter @Inject constructor(
    private val clearLoansUseCase: ClearLoansUseCase,
    private val writeTokenUseCase: WriteTokenUseCase
) : BasePresenter<LogoutView>() {
    fun logout() {
        clearLoansUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    writeTokenUseCase("")
                    view?.navigateTo(R.id.action_logoutDialogFragment_to_registrationFragment)
                },
                {
                    view?.showError(it)
                }
            ).untilDestroy()
    }

    fun cancel() {
        view?.returnToPreviousScreen()
    }
}