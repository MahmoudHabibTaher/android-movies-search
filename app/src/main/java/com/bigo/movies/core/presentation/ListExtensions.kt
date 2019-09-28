package com.bigo.movies.core.presentation

fun <T> List<T>.toCommaSeparated() = foldIndexed("") { index, acc, item ->
    acc + if (index < size - 1) {
        "$item, "
    } else {
        item.toString()
    }
}