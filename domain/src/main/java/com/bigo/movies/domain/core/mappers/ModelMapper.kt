package com.bigo.movies.domain.core.mappers

interface ModelMapper<From, To> {
    fun map(model: From): To
}