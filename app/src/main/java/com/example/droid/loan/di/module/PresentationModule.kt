package com.example.droid.loan.di.module

import com.example.droid.loan.presentation.presenter.*
import dagger.Binds
import dagger.Module

@Module
interface PresentationModule {
    @Binds
    fun bindRegistrationPresenter(registrationPresenterImpl: RegistrationPresenterImpl): RegistrationPresenter

    @Binds
    fun bindLoginPresenter(loginPresenterImpl: LoginPresenterImpl): LoginPresenter

    @Binds
    fun bindPersonalAreaPresenter(personalAreaPresenterImpl: PersonalAreaPresenterImpl): PersonalAreaPresenter

    @Binds
    fun bindCreateLoanPresenter(createLoanPresenterImpl: CreateLoanPresenterImpl): CreateLoanPresenter

    @Binds
    fun bindLoanDetailsPresenter(loanDetailsPresenterImpl: LoanDetailsPresenterImpl): LoanDetailsPresenter

    @Binds
    fun bindResultPresenter(resultPresenterImpl: ResultPresenterImpl): ResultPresenter

    @Binds
    fun bindLogoutPresenter(logoutPresenterImpl: LogoutPresenterImpl): LogoutPresenter
}