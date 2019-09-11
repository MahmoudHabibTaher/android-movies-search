package com.bigo.data.movies.local

import com.bigo.data.file.FileReader
import com.bigo.data.movies.exceptions.LoadMoviesException
import com.bigo.data.parsers.JsonParser
import com.bigo.data.parsers.excpetions.JsonParseException
import com.bigo.moviessearch.domain.entities.Actor
import com.bigo.moviessearch.domain.entities.Genre
import com.bigo.moviessearch.domain.entities.Movie
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException
import java.io.IOException

class MoviesLocalDataSourceTest {

    private val fileReader = mock<FileReader>()
    private val jsonParser = mock<JsonParser>()

    private val fileName = "movies.json"
    private val content =
        "{\"movies\":[{\"title\":\"(500) Days of Summer\", \"year\":2009 , \"cast\": [\"Joseph Gordon-Levitt\",\"Zooey Deschanel\"],\"genres\": [\"Romance\",\"Comedy\"],\"rating\": 4 }]}"
    private val movies = listOf(
        Movie.Builder()
            .title("(500) Days of Summer")
            .year(2009)
            .cast(listOf(Actor("Joseph Gordon-Levitt"), Actor("Zooey Deschanel")))
            .genres(listOf(Genre("Romance"), Genre("Comedy")))
            .rating(4)
            .build()
    )

    private lateinit var dataSource: MoviesLocalDataSource

    @Before
    fun setUp() {
        dataSource = MoviesLocalDataSource(fileReader, jsonParser)
    }

    @Test
    fun testReadFileContentAndParse() {
        whenever(fileReader.readFile(fileName)) doReturn content
        whenever(jsonParser.parse<List<Movie>>(content)) doReturn movies

        val result = dataSource.loadMovies().blockingGet()

        verify(fileReader).readFile(fileName)
        verify(jsonParser).parse<List<Movie>>(content)

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
        whenever(jsonParser.parse<List<Movie>>(content)) doThrow throwable

        val testObserver = dataSource.loadMovies().test()

        testObserver.assertError {
            it is LoadMoviesException && it.message == message && it.cause == throwable
        }
    }
}