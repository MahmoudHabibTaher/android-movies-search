package com.bigo.data.movies.local

import com.bigo.data.file.FileReader
import com.bigo.data.movies.exceptions.LoadMoviesException
import com.bigo.data.movies.local.entities.MovieLocal
import com.bigo.data.movies.local.entities.MoviesPage
import com.bigo.data.parsers.JsonParser
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.core.mappers.mapWith
import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.MoviesDataSource
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import io.reactivex.Single
import java.lang.Exception

class MoviesLocalDataSource(
    private val fileReader: FileReader,
    private val jsonParser: JsonParser,
    private val movieModelMapper: ModelMapper<MovieLocal, Movie>
) : MoviesDataSource {
    override fun loadMovies(): Single<List<Movie>> =
        Single.create { emitter ->
            try {
                val content = fileReader.readFile("movies.json")
                val page = jsonParser.parse(content, MoviesPage::class.java)
                val movies = page?.movies?.mapWith(movieModelMapper) ?: listOf()
                emitter.onSuccess(movies)
            } catch (ex: Exception) {
                emitter.onError(LoadMoviesException(ex.message, ex))
            }
        }

    override fun loadMovieImages(title: String, page: Int): Single<PagedList<MovieImage>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}