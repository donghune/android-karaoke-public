package com.github.donghune.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MananaSongResponse(
    val brand: String,
    val no: String,
    val title: String,
    val singer: String,
    val composer: String,
    val lyricist: String,
    val release: String
)
