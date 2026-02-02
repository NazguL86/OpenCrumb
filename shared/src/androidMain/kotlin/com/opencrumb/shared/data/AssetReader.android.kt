package com.opencrumb.shared.data

import android.content.Context

private lateinit var appContext: Context

fun initAndroidContext(context: Context) {
    appContext = context.applicationContext
}

actual fun readAssetFile(fileName: String): String {
    return appContext.assets.open(fileName).bufferedReader().use { it.readText() }
}
