package com.example.droid.loan.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {
    @Provides
    fun provideContext(): Context =
        context
}