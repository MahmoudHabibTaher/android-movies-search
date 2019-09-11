package com.bigo.moviessearch.domain.search

import com.bigo.moviessearch.domain.entities.Movie
import io.reactivex.rxjava3.core.Single

interface MoviesDataSource {
    fun loadMovies(): Single<List<Movie>>
}