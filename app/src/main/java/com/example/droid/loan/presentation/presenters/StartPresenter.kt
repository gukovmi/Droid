package com.example.droid.loan.presentation.presenters

import com.example.droid.R
import com.example.droid.loan.domain.use_cases.ReadTokenUseCase
import com.example.droid.loan.presentation.base.BasePresenter
import com.example.droid.loan.ui.fragments.StartView
import javax.inject.Inject

class StartPresenterImpl @Inject constructor(
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