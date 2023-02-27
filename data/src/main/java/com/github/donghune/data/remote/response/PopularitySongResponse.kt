package com.github.donghune.data.remote.response

data class PopularitySongResponse(
    val rank: Int,
    val number: Int,
    val title: String,
    val singer: String
)
