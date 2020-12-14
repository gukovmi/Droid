package com.example.droid.loan

import android.app.Application
import android.content.Context
import com.example.droid.loan.di.component.AppComponent
import com.example.droid.loan.di.component.DaggerAppComponent
import com.example.droid.loan.di.module.ContextModule

class App : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this.applicationContext))
            .build()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleContextWrapper(newBase).setLocale())
    }
}