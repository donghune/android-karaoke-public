package com.github.donghune.domain.entity

enum class SearchType(
    val korName: String
) {
    TITLE("제목"),
    SINGER("가수"),
    TITLE_WITH_SINGER("제목 + 가수"),
}
