package com.bigo.data.movies

import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.search.MoviesDataSource
import io.reactivex.Single

class MoviesRepository(private val localDataSource: MoviesDataSource) : MoviesDataSource {
    private val movies = mutableListOf<Movie>()

    override fun loadMovies(): Single<List<Movie>> =
        if (movies.isNotEmpty()) {
            Single.fromCallable {
                movies
            }
        } else {
            localDataSource.loadMovies().doOnSuccess {
                movies.addAll(it)
            }
        }
}