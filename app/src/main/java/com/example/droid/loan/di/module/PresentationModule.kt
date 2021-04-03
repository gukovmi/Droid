package com.example.droid.loan.di.module

import com.example.droid.loan.domain.usecase.*
import com.example.droid.loan.domain.usecase.auth.LoginUseCase
import com.example.droid.loan.domain.usecase.auth.RegistrationUseCase
import com.example.droid.loan.domain.usecase.info.*
import com.example.droid.loan.domain.usecase.loan.*
import com.example.droid.loan.presentation.presenter.*
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    fun provideStartPresenter(readTokenUseCase: ReadTokenUseCase): StartPresenterImpl =
        StartPresenterImpl(readTokenUseCase)

    @Provides
    fun provideRegistrationPresenter(
        registrationUseCase: RegistrationUseCase,
        fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): RegistrationPresenterImpl =
        RegistrationPresenterImpl(
            registrationUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun provideLoginPresenter(
        loginUseCase: LoginUseCase,
        writeTokenUseCase: WriteTokenUseCase,
        writeNameUseCase: WriteNameUseCase,
        fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): LoginPresenterImpl =
        LoginPresenterImpl(
            loginUseCase,
            writeTokenUseCase,
            writeNameUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun providePersonalAreaPresenter(
        getLoansUseCase: GetLoansUseCase,
        updateLoansUseCase: UpdateLoansUseCase,
        getLoansConditionsUseCase: GetLoanConditionsUseCase,
        readTokenUseCase: ReadTokenUseCase,
        readNameUseCase: ReadNameUseCase,
        readInstructionWasShownUseCase: ReadInstructionWasShownUseCase,
        writeInstructionWasShownUseCase: WriteInstructionWasShownUseCase,
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): PersonalAreaPresenterImpl =
        PersonalAreaPresenterImpl(
            getLoansUseCase,
            updateLoansUseCase,
            getLoansConditionsUseCase,
            readTokenUseCase,
            readNameUseCase,
            readInstructionWasShownUseCase,
            writeInstructionWasShownUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun provideCreateLoanPresenter(
        readTokenUseCase: ReadTokenUseCase,
        createLoanUseCase: CreateLoanUseCase,
        amountIsValidUseCase: AmountIsValidUseCase,
        fieldsIsNotEmptyUseCase: FieldsIsNotEmptyUseCase,
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): CreateLoanPresenterImpl =
        CreateLoanPresenterImpl(
            readTokenUseCase,
            createLoanUseCase,
            amountIsValidUseCase,
            fieldsIsNotEmptyUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun provideLoanDetailsPresenter(
        readTokenUseCase: ReadTokenUseCase,
        getLoanByIdUseCase: GetLoanByIdUseCase,
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): LoanDetailsPresenterImpl =
        LoanDetailsPresenterImpl(
            readTokenUseCase,
            getLoanByIdUseCase,
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun provideResultPresenter(
        writeLanguageUseCase: WriteLanguageUseCase,
        readLanguageUseCase: ReadLanguageUseCase
    ): ResultPresenterImpl =
        ResultPresenterImpl(
            writeLanguageUseCase,
            readLanguageUseCase
        )

    @Provides
    fun provideLogoutPresenter(
        clearLoansUseCase: ClearLoansUseCase,
        writeTokenUseCase: WriteTokenUseCase
    ): LogoutPresenterImpl =
        LogoutPresenterImpl(
            clearLoansUseCase,
            writeTokenUseCase
        )


}