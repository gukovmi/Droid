package com.example.droid.loan.presentation.presenter

import com.example.droid.R
import com.example.droid.loan.domain.usecase.info.ReadTokenUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragment.StartView
import javax.inject.Inject

class StartPresenter @Inject constructor(
    private val readTokenUseCase: ReadTokenUseCase
) : BasePresenter<StartView>() {
    override fun onViewAttached() {
        if (readTokenUseCase().isNotEmpty()) {
            view?.navigateTo(R.id.action_startFragment_to_personalAreaFragment)
        } else {
            view?.navigateTo(R.id.action_startFragment_to_registrationFragment)
        }
    }
}