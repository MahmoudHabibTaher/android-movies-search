package com.bigo.movies.domain.search

import com.bigo.movies.domain.entities.Movie
import io.reactivex.Single

interface MoviesDataSource {
    fun loadMovies(): Single<List<Movie>>
}