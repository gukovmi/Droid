package com.example.droid.loan.di.component

import com.example.droid.loan.di.module.ContextModule
import com.example.droid.loan.di.module.DataModule
import com.example.droid.loan.di.module.DomainModule
import com.example.droid.loan.di.module.PresentationModule
import com.example.droid.loan.ui.fragment.*
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