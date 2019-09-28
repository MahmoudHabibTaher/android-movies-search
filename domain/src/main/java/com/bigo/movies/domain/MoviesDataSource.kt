package com.bigo.movies.domain

import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import io.reactivex.Single

interface MoviesDataSource {
    fun loadMovies(): Single<List<Movie>>

    fun loadMovieImages(title: String, page: Int): Single<PagedList<MovieImage>>
}