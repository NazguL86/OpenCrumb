package com.opencrumb.shared.data

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun getDeviceLanguage(): String {
    val locale = NSLocale.currentLocale
    val language = locale.languageCode
    return when (language) {
        "es", "fr", "de", "it", "pt" -> language
        else -> "en"
    }
}
