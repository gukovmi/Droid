package com.example.droid.loan

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LocaleContextWrapper(val context: Context?) {
    fun setLocale(): Context? =
        if (context != null) {
            val locale = Locale(readLanguage())
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else context

    private fun readLanguage(): String =
        context?.getSharedPreferences(
            "com.example.droid.prefs",
            Context.MODE_PRIVATE
        )?.getString("language", "ru") ?: "ru"
}