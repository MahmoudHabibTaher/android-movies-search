package com.bigo.movies.domain.core.mappers

infix fun <From : Any, To> From.mapWith(modelMapper: ModelMapper<From, To>) =
    modelMapper.map(this)

infix fun <From : Any, To> List<From>.mapWith(modelMapper: ModelMapper<From, To>) =
    map { it mapWith modelMapper }