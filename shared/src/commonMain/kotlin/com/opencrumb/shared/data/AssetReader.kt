package com.opencrumb.shared.data

import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
suspend fun readAssetFile(fileName: String): String {
    return opencrumb.shared.generated.resources.Res.readBytes("files/$fileName")
        .decodeToString()
}
