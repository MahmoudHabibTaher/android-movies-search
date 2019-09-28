package com.bigo.data.movies

import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.MoviesDataSource
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import io.reactivex.Single

class MoviesRepository(
    private val localDataSource: MoviesDataSource,
    private val remoteDataSource: MoviesDataSource
) :
    MoviesDataSource {
    private val movies = mutableSetOf<Movie>()

    override fun loadMovies(): Single<List<Movie>> =
        if (movies.isNotEmpty()) {
            Single.fromCallable {
                movies.toList()
            }
        } else {
            localDataSource.loadMovies().doOnSuccess {
                movies.addAll(it)
            }
        }

    override fun loadMovieImages(title: String, page: Int): Single<PagedList<MovieImage>> =
        remoteDataSource.loadMovieImages(title, page)
}