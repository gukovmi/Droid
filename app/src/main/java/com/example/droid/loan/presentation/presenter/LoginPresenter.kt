package com.example.droid.loan.presentation.presenter

import android.os.Bundle
import com.example.droid.R
import com.example.droid.loan.domain.entity.Auth
import com.example.droid.loan.domain.usecase.FieldsIsNotEmptyUseCase
import com.example.droid.loan.domain.usecase.auth.LoginUseCase
import com.example.droid.loan.domain.usecase.info.ReadLanguageUseCase
import com.example.droid.loan.domain.usecase.info.WriteLanguageUseCase
import com.example.droid.loan.domain.usecase.info.WriteNameUseCase
import com.example.droid.loan.domain.usecase.info.WriteTokenUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val writeTokenUseCase: WriteTokenUseCase,
    private val writeNameUseCase: WriteNameUseCase,
    private val fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : BasePresenter<LoginView>() {
    fun login(name: String, password: String) {
        if (fieldsIsNotEmptyUseCase(listOf(name, password))) {
            val auth =
                Auth(name, password)
            view?.startLoading()
            loginUseCase(auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    writeTokenUseCase(it)
                    writeNameUseCase(auth.name)
                    val bundle = Bundle().apply {
                        putBoolean("fromLogin", true)
                    }
                    view?.finishLoading()
                    view?.navigateToWithBundle(
                        R.id.action_loginFragment_to_personalAreaFragment,
                        bundle
                    )
                }, {
                    view?.finishLoading()
                    view?.showError(it)
                }).untilDestroy()
        } else view?.showEmptyFieldsWarning()
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