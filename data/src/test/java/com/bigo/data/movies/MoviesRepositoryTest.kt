package com.bigo.data.movies

import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.MoviesDataSource
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class MoviesRepositoryTest {

    private val localDataSource = mock<MoviesDataSource>()
    private val remoteDataSource = mock<MoviesDataSource>()

    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        clearInvocations(localDataSource)

        moviesRepository = MoviesRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun loadMoviesWithEmptyCacheReturnFromLocal() {
        val movies = listOf(
            buildMovie("First movie", 2009),
            buildMovie("Second movie", 2010),
            buildMovie("Third movie", 2011)
        )

        setMovies(movies)

        val result = moviesRepository.loadMovies().blockingGet()

        verify(localDataSource).loadMovies()

        assertEquals(movies, result)
    }

    @Test
    fun loadMoviesFromLocalAddToCache() {
        val movies = listOf(
            buildMovie("First movie", 2009),
            buildMovie("Second movie", 2010),
            buildMovie("Third movie", 2011)
        )

        setMovies(movies)

        var result = moviesRepository.loadMovies().blockingGet()

        verify(localDataSource).loadMovies()

        assertEquals(movies, result)

        clearInvocations(localDataSource)

        result = moviesRepository.loadMovies().blockingGet()

        verifyNoMoreInteractions(localDataSource)

        assertEquals(movies, result)
    }

    private fun setMovies(movies: List<Movie>) {
        whenever(localDataSource.loadMovies()) doReturn Single.just(movies)
    }

    private fun buildMovie(title: String, year: Int) =
        Movie.Builder()
            .title(title)
            .year(year)
            .build()
}