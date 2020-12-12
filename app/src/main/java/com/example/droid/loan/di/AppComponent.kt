package com.example.droid.loan.di

import com.example.droid.loan.di.modules.ContextModule
import com.example.droid.loan.di.modules.DataModule
import com.example.droid.loan.di.modules.DomainModule
import com.example.droid.loan.di.modules.PresentationModule
import com.example.droid.loan.ui.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, PresentationModule::class, ContextModule::class])
interface AppComponent {
    fun injectStartFragment(startFragment: StartFragment)
    fun injectRegistrationFragment(registrationFragment: RegistrationFragment)
    fun injectLoginFragment(loginFragment: LoginFragment)
    fun injectPersonalAreaFragment(personalAreaFragment: PersonalAreaFragment)
    fun injectCreateLoanFragment(createLoanFragment: CreateLoanFragment)
    fun injectLoanDetailsFragment(loanDetailsFragment: LoanDetailsFragment)
    fun injectResultFragment(resultFragment: ResultFragment)
    fun injectLogoutDialogFragment(logoutDialogFragment: LogoutDialogFragment)
}