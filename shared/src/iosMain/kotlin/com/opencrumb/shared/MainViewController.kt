package com.opencrumb.shared

import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {
    App(onExternalUrl = { url ->
        NSURL.URLWithString(url)?.let { nsUrl ->
            UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any>(), null)
        }
    })
}

