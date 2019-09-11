package com.bigo.moviessearch.domain.search

import com.bigo.moviessearch.domain.core.executors.PostThreadExecutor
import com.bigo.moviessearch.domain.core.executors.ThreadExecutor
import com.bigo.moviessearch.domain.core.usecase.UseCase
import com.bigo.moviessearch.domain.entities.Movie
import com.bigo.moviessearch.domain.entities.SearchResult
import io.reactivex.rxjava3.core.Single

class SearchMovies(
    threadExecutor: ThreadExecutor,
    postThreadExecutor: PostThreadExecutor,
    private val moviesDataSource: MoviesDataSource
) : UseCase<List<SearchResult>, SearchMoviesParams>(threadExecutor, postThreadExecutor) {
    override fun buildUseCase(args: SearchMoviesParams?): Single<List<SearchResult>> =
        args?.query?.let { query ->
            if (query.isBlank()) {
                emptyResult()
            } else {
                moviesDataSource.loadMovies()
                    .map { movies ->
                        filterMoviesByQuery(query, movies)
                    }
                    .map(::groupMoviesByYear)
            }
        } ?: emptyResult()

    private fun emptyResult(): Single<List<SearchResult>> = Single.just(emptyList<SearchResult>())

    private fun filterMoviesByQuery(query: String, movies: List<Movie>) =
        movies.filter { movie -> movie.title.contains(query, true) }

    private fun groupMoviesByYear(movies: List<Movie>) =
        movies.groupBy { movie ->
            movie.year
        }.map { (year, movies) ->
            buildResult(year, movies)
        }

    private fun buildResult(year: Int, movies: List<Movie>) =
        SearchResult.Builder()
            .year(year)
            .movies(movies)
            .build()
}