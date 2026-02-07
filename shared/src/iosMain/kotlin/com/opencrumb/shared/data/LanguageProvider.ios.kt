package com.opencrumb.shared.data

import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

actual fun getDeviceLanguage(): String {
    val preferredLanguage = NSLocale.preferredLanguages.firstOrNull() as? String ?: "en"
    val language = preferredLanguage.substringBefore("-").substringBefore("_")
    return when (language) {
        "es", "fr", "de", "it", "pt" -> language
        else -> "en"
    }
}
