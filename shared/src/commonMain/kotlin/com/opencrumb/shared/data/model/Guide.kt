package com.opencrumb.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Guide(
    val id: Int,
    val title: String,
    val description: String,
    val content: String,
    val imageRes: String
)
