package com.opencrumb.shared.data

import android.content.Context
import java.util.Locale

private lateinit var appContext: Context

fun initAndroidContext(context: Context) {
    appContext = context.applicationContext
}

actual fun getDeviceLanguage(): String {
    val locale = Locale.getDefault()
    return when (locale.language) {
        "es", "fr", "de", "it", "pt" -> locale.language
        else -> "en"
    }
}
