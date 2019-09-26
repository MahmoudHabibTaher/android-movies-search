package com.bigo.data.movies.local.mappers

import com.bigo.data.movies.local.entities.MovieLocal
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.entities.Actor
import com.bigo.movies.domain.entities.Genre
import com.bigo.movies.domain.entities.Movie

class MovieLocalModelMapper : ModelMapper<MovieLocal, Movie> {
    override fun map(model: MovieLocal): Movie =
        Movie.Builder()
            .title(model.title ?: "")
            .year(model.year ?: 0)
            .cast(model.cast?.map { actor ->
                Actor(actor)
            } ?: listOf())
            .genres(model.genres?.map { genre ->
                Genre(genre)
            } ?: listOf())
            .rating(model.rating ?: 0)
            .build()
}