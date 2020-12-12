package com.example.droid.loan

import android.app.Application
import android.content.Context
import com.example.droid.loan.di.AppComponent
import com.example.droid.loan.di.DaggerAppComponent
import com.example.droid.loan.di.modules.ContextModule

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