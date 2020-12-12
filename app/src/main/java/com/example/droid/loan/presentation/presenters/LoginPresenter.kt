package com.example.droid.loan.presentation.presenters

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.use_cases.*
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface LoginPresenter {
    fun login(name: String, password: String)
    fun setLanguage(language: String)
    fun returnToPreviousScreen()
    fun showHelpDialog()
}

class LoginPresenterImpl @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val writeTokenUseCase: WriteTokenUseCase,
    private val writeNameUseCase: WriteNameUseCase,
    private val fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : LoginPresenter, BasePresenter<LoginView>() {
    override fun login(name: String, password: String) {
        if (fieldsIsNotEmptyUseCase(listOf(name, password))) {
            val auth =
                Auth(name, password)
            view?.startLoginLoading()
            loginUseCase(auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    writeTokenUseCase(it)
                    writeNameUseCase(auth.name)
                    val bundle = Bundle().apply {
                        putBoolean("fromLogin", true)
                    }
                    view?.finishLoginLoading()
                    view?.navigateToWithBundle(
                        R.id.action_loginFragment_to_personalAreaFragment,
                        bundle
                    )
                }, {
                    view?.finishLoginLoading()
                    view?.showError(it)
                }).untilDestroy()
        } else view?.showEmptyFieldsWarning()
    }

    override fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    override fun returnToPreviousScreen() {
        view?.navigateTo(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }
}