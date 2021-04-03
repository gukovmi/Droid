package com.example.droid.loan.di.module

import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.domain.repository.AuthRepository
import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.repository.LoanRepository
import com.example.droid.loan.domain.usecase.*
import com.example.droid.loan.domain.usecase.auth.LoginUseCase
import com.example.droid.loan.domain.usecase.auth.RegistrationUseCase
import com.example.droid.loan.domain.usecase.info.*
import com.example.droid.loan.domain.usecase.loan.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideRegistrationUseCase(authRepository: AuthRepository): RegistrationUseCase =
        RegistrationUseCase(
            authRepository
        )

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(
            authRepository
        )

    @Provides
    fun provideGetLoansUseCase(loanRepository: LoanRepository): GetLoansUseCase =
        GetLoansUseCase(
            loanRepository
        )

    @Provides
    fun provideUpdateLoansUseCase(loanRepository: LoanRepository): UpdateLoansUseCase =
        UpdateLoansUseCase(loanRepository)

    @Provides
    fun provideCreateLoanUseCase(loanRepository: LoanRepository): CreateLoanUseCase =
        CreateLoanUseCase(
            loanRepository
        )

    @Provides
    fun provideGetLoanByIdUseCase(loanRepository: LoanRepository): GetLoanByIdUseCase =
        GetLoanByIdUseCase(
            loanRepository
        )

    @Provides
    fun provideGetLoanConditionsUseCase(loanRepository: LoanRepository): GetLoanConditionsUseCase =
        GetLoanConditionsUseCase(
            loanRepository
        )

    @Provides
    fun provideClearLoansUseCase(loanRepository: LoanRepository): ClearLoansUseCase =
        ClearLoansUseCase(
            loanRepository
        )

    @Provides
    fun provideWriteTokenUseCase(infoRepository: InfoRepository): WriteTokenUseCase =
        WriteTokenUseCase(
            infoRepository
        )

    @Provides
    fun provideReadTokenUseCase(infoRepository: InfoRepository): ReadTokenUseCase =
        ReadTokenUseCase(
            infoRepository
        )

    @Provides
    fun provideReadNameUseCase(infoRepository: InfoRepository): ReadNameUseCase =
        ReadNameUseCase(
            infoRepository
        )

    @Provides
    fun provideWriteNameUseCase(infoRepository: InfoRepository): WriteNameUseCase =
        WriteNameUseCase(
            infoRepository
        )

    @Provides
    fun provideReadInstructionWasShownUseCase(infoRepository: InfoRepository): ReadInstructionWasShownUseCase =
        ReadInstructionWasShownUseCase(
            infoRepository
        )

    @Provides
    fun provideWriteInstructionWasShownUseCase(infoRepository: InfoRepository): WriteInstructionWasShownUseCase =
        WriteInstructionWasShownUseCase(
            infoRepository
        )

    @Provides
    fun provideWriteLanguageUseCase(infoRepository: InfoRepository): WriteLanguageUseCase =
        WriteLanguageUseCase(
            infoRepository
        )

    @Provides
    fun provideReadLanguageUseCase(infoRepository: InfoRepository): ReadLanguageUseCase =
        ReadLanguageUseCase(
            infoRepository
        )

    @Provides
    fun provideAmountIsValidUseCase(): AmountIsValidUseCase =
        AmountIsValidUseCase()

    @Provides
    fun provideFieldsIsNotEmptyUseCase(): FieldsIsNotEmptyUseCase =
        FieldsIsNotEmptyUseCase()

    @Provides
    fun provideDataConverter(): DataConverter =
        DataConverter()
}