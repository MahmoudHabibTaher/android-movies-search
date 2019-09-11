package com.bigo.data.movies

import com.bigo.moviessearch.domain.entities.Movie
import com.bigo.moviessearch.domain.search.MoviesDataSource
import io.reactivex.rxjava3.core.Single

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