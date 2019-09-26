package com.bigo.data.movies.local

import com.bigo.data.file.FileReader
import com.bigo.data.movies.exceptions.LoadMoviesException
import com.bigo.data.movies.local.entities.MovieLocal
import com.bigo.data.movies.local.entities.MoviesPage
import com.bigo.data.parsers.JsonParser
import com.bigo.data.parsers.excpetions.JsonParseException
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.entities.Actor
import com.bigo.movies.domain.entities.Genre
import com.bigo.movies.domain.entities.Movie
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class MoviesLocalDataSourceTest {

    private val fileReader = mock<FileReader>()
    private val jsonParser = mock<JsonParser>()
    private val movieModelMapper = mock<ModelMapper<MovieLocal, Movie>>()

    private val fileName = "movies.json"
    private val content =
        "{\"movies\":[{\"title\":\"(500) Days of Summer\", \"year\":2009 , \"cast\": [\"Joseph Gordon-Levitt\",\"Zooey Deschanel\"],\"genres\": [\"Romance\",\"Comedy\"],\"rating\": 4 }]}"
    private val movieLocal = MovieLocal.Builder()
        .title("(500) Days of Summer")
        .year(2009)
        .cast(listOf("Joseph Gordon-Levitt", "Zooey Deschanel"))
        .genres(listOf("Romance", "Comedy"))
        .rating(4)
        .build()
    private val movie = Movie.Builder()
        .title("(500) Days of Summer")
        .year(2009)
        .cast(listOf(Actor("Joseph Gordon-Levitt"), Actor("Zooey Deschanel")))
        .genres(listOf(Genre("Romance"), Genre("Comedy")))
        .rating(4)
        .build()

    private val moviesLocal = listOf(
        movieLocal
    )

    private val movies = listOf(
        movie
    )

    private val moviesPage = MoviesPage(moviesLocal)

    private lateinit var dataSource: MoviesLocalDataSource

    @Before
    fun setUp() {
        dataSource = MoviesLocalDataSource(fileReader, jsonParser, movieModelMapper)
    }

    @Test
    fun testReadFileContentAndParse() {
        whenever(fileReader.readFile(fileName)) doReturn content
        whenever(jsonParser.parse(content, MoviesPage::class.java)) doReturn moviesPage
        whenever(movieModelMapper.map(any())) doReturn movie

        val result = dataSource.loadMovies().blockingGet()

        verify(fileReader).readFile(fileName)
        verify(jsonParser).parse(content, MoviesPage::class.java)
        verify(movieModelMapper, times(movies.size)).map(any())

        assertEquals(movies, result)
    }

    @Test
    fun testLoadMoviesOnGetContentError() {
        val message = "File not found"
        val throwable = FileNotFoundException(message)
        whenever(fileReader.readFile(fileName)) doThrow throwable

        val testObserver = dataSource.loadMovies().test()

        testObserver.assertError {
            it is LoadMoviesException && it.message == message && it.cause == throwable
        }
    }

    @Test
    fun testLoadMoviesOnParseError() {
        whenever(fileReader.readFile(fileName)) doReturn content

        val message = "Parsing error"
        val throwable = JsonParseException(message)
        whenever(jsonParser.parse(content, MoviesPage::class.java)) doThrow throwable

        val testObserver = dataSource.loadMovies().test()

        testObserver.assertError {
            it is LoadMoviesException && it.message == message && it.cause == throwable
        }
    }
}