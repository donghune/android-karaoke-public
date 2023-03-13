package com.github.donghune.domain.entity

data class PlayList(
    val id: Int,
    val name: String,
    val songNumbers: List<Int>
)
