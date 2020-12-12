package com.example.droid.loan.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.entities.Auth
import com.example.droid.loan.domain.use_cases.FieldsIsNotEmptyUseCase
import com.example.droid.loan.domain.use_cases.ReadLanguageUseCase
import com.example.droid.loan.domain.use_cases.RegistrationUseCase
import com.example.droid.loan.domain.use_cases.WriteLanguageUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.RegistrationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface RegistrationPresenter {
    fun registration(name: String, password: String)
    fun skipRegistration()
    fun setLanguage(language: String)
    fun showHelpDialog()
}

class RegistrationPresenterImpl @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
    private val writeLanguageUseCase: WriteLanguageUseCase,
    private val readLanguageUseCase: ReadLanguageUseCase
) : RegistrationPresenter, BasePresenter<RegistrationView>() {
    override fun registration(name: String, password: String) {
        if (fieldsIsNotEmptyUseCase(listOf(name, password))) {
            view?.startRegistrationLoading()
            registrationUseCase(
                Auth(
                    name,
                    password
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.finishRegistrationLoading()
                    view?.navigateTo(R.id.action_registrationFragment_to_loginFragment)
                }, {
                    view?.finishRegistrationLoading()
                    view?.showError(it)
                }).untilDestroy()
        } else view?.showEmptyFieldsWarning()
    }

    override fun skipRegistration() {
        view?.navigateTo(R.id.action_registrationFragment_to_loginFragment)
    }

    override fun setLanguage(language: String) {
        if (readLanguageUseCase() != language) {
            writeLanguageUseCase(language)
            view?.recreateRequireActivity()
        }
    }

    override fun showHelpDialog() {
        view?.navigateTo(R.id.helpDialogFragment)
    }
}