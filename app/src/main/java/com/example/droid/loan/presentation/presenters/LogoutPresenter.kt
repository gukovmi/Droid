package com.example.droid.loan.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.ClearLoansUseCase
import com.example.droid.loan.domain.use_cases.WriteTokenUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.LogoutView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface LogoutPresenter {
    fun logout()
    fun cancel()
}

class LogoutPresenterImpl @Inject constructor(
    private val clearLoansUseCase: ClearLoansUseCase,
    private val writeTokenUseCase: WriteTokenUseCase
) : LogoutPresenter, BasePresenter<LogoutView>() {
    override fun logout() {
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

    override fun cancel() {
        view?.returnToPreviousScreen()
    }
}