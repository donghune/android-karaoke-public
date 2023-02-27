package com.github.donghune.data.remote.util

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

fun Elements.toList(): List<Element> {
    return mutableListOf<Element>()
        .apply { this@toList.forEach { element -> add(element) } }
        .toList()
}
