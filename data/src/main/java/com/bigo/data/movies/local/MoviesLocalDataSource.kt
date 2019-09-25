package com.bigo.data.movies.local

import com.bigo.data.file.FileReader
import com.bigo.data.movies.exceptions.LoadMoviesException
import com.bigo.data.parsers.JsonParser
import com.bigo.moviessearch.domain.entities.Movie
import com.bigo.moviessearch.domain.search.MoviesDataSource
import io.reactivex.rxjava3.core.Single
import java.lang.Exception

class MoviesLocalDataSource(
    private val fileReader: FileReader,
    private val jsonParser: JsonParser
) : MoviesDataSource {
    override fun loadMovies(): Single<List<Movie>> =
        Single.create { emitter ->
            try {
                val content = fileReader.readFile("movies.json")
                val page = jsonParser.parse(content, MoviesPage::class.java)
                val movies = page?.movies ?: listOf()
                emitter.onSuccess(movies)
            } catch (ex: Exception) {
                emitter.onError(LoadMoviesException(ex.message, ex))
            }
        }
}