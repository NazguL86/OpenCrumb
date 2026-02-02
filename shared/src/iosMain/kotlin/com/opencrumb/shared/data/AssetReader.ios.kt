package com.opencrumb.shared.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

@OptIn(ExperimentalForeignApi::class)
actual fun readAssetFile(fileName: String): String {
    val filePath = NSBundle.mainBundle.pathForResource(fileName.substringBeforeLast("."), fileName.substringAfterLast("."))
    return filePath?.let {
        NSString.stringWithContentsOfFile(it, NSUTF8StringEncoding, null) as String
    } ?: ""
}
