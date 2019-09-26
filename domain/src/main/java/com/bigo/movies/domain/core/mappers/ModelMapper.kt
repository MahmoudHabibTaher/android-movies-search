package com.bigo.movies.domain.core.mappers

interface ModelMapper<in From, out To> {
    fun map(model: From): To
}