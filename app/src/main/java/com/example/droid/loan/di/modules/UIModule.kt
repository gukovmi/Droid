package com.example.droid.loan.di.modules

import android.content.Context
import com.example.droid.loan.ui.converters.OffsetDateTimeConverter
import com.example.droid.loan.ui.converters.ThrowableConverter
import dagger.Module
import dagger.Provides

@Module
class UIModule {
    @Provides
    fun provideThrowableConverter(context: Context): ThrowableConverter =
        ThrowableConverter(context)

    @Provides
    fun provideOffsetDateTimeConverter(context: Context): OffsetDateTimeConverter =
        OffsetDateTimeConverter(context)
}