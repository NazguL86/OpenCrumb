package com.opencrumb.shared.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

@OptIn(ExperimentalForeignApi::class)
actual fun readAssetFile(fileName: String): String {
    val bundlePath = NSBundle.mainBundle.bundlePath
    val filePath = "$bundlePath/$fileName"
    
    return try {
        NSString.stringWithContentsOfFile(filePath, NSUTF8StringEncoding, null) as String
    } catch (e: Exception) {
        ""
    }
}
