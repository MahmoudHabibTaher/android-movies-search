package com.bigo.moviessearch.domain.search

import com.bigo.moviessearch.domain.core.executors.TestPostThreadExecutor
import com.bigo.moviessearch.domain.core.executors.TestThreadExecutor
import com.bigo.moviessearch.domain.entities.Movie
import com.bigo.moviessearch.domain.entities.SearchResult
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class SearchMoviesTest {

    private val threadExecutor = TestThreadExecutor()

    private val postThreadExecutor = TestPostThreadExecutor()

    private val moviesDataSource = mock<MoviesDataSource>()

    private lateinit var searchMovies: SearchMovies

    @Before
    fun setUp() {
        searchMovies = SearchMovies(threadExecutor, postThreadExecutor, moviesDataSource)
    }

    @Test
    fun testSearchWithBlankQueryReturnsEmptyResult() {
        val query = ""

        val params = SearchMoviesParams(query)

        val result = searchMovies.execute(params).blockingGet()

        assertEquals(result, emptyList<SearchResult>())
    }

    @Test
    fun testSearchWithQueryFiltersMovieByQuery() {
        var movies = listOf(
            buildMovie("first movie", 2009),
            buildMovie("second movie", 2009),
            buildMovie("third movie", 2009)
        )

        setMovies(movies)

        var query = "non-existent"

        var params = SearchMoviesParams(query)

        var result = searchMovies.execute(params).blockingGet()

        assertEquals(
            listOf<SearchResult>(), result
        )

        movies = listOf(
            buildMovie("first movie", 2009),
            buildMovie("second movie", 2009),
            buildMovie("third movie", 2009)
        )

        setMovies(movies)

        query = "movie"

        params = SearchMoviesParams(query)

        result = searchMovies.execute(params).blockingGet()

        assertEquals(
            listOf(buildResult(2009, movies)), result
        )

        movies = listOf(
            buildMovie("first movie", 2009),
            buildMovie("second movie", 2009),
            buildMovie("third movie", 2009),
            buildMovie("action film", 2009)
        )

        setMovies(movies)

        query = "film"

        params = SearchMoviesParams(query)

        result = searchMovies.execute(params).blockingGet()

        assertEquals(
            listOf(
                buildResult(
                    2009, listOf(
                        buildMovie("action film", 2009)
                    )
                )
            ), result
        )
    }

    @Test
    fun testSearchMoviesGroupByYear() {
        val movies = listOf(
            buildMovie("first movie", 2009),
            buildMovie("second movie", 2009),
            buildMovie("third movie", 2009),
            buildMovie("action movie", 2008)
        )

        setMovies(movies)

        val query = "movie"

        val params = SearchMoviesParams(query)

        val result = searchMovies.execute(params).blockingGet()

        assertEquals(
            listOf(
                buildResult(
                    2009, listOf(
                        buildMovie("first movie", 2009),
                        buildMovie("second movie", 2009),
                        buildMovie("third movie", 2009)
                    )
                ),
                buildResult(
                    2008, listOf(
                        buildMovie("action movie", 2008)
                    )
                )
            ), result
        )
    }

    private fun setMovies(movies: List<Movie>) {
        whenever(moviesDataSource.loadMovies()) doReturn Single.just(movies)
    }

    private fun buildMovie(title: String, year: Int) =
        Movie.Builder()
            .title(title)
            .year(year)
            .build()

    private fun buildResult(year: Int, movies: List<Movie>) =
        SearchResult.Builder()
            .year(year)
            .movies(movies)
            .build()
}