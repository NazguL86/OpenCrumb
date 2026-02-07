package com.opencrumb.shared.data

import opencrumb.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
suspend fun readAssetFile(fileName: String): String {
    return Res.readBytes("files/$fileName").decodeToString()
}
