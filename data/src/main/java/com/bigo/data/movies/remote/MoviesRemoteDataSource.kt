package com.bigo.data.movies.remote

import com.bigo.data.movies.remote.entities.FlickrPhotos
import com.bigo.movies.domain.MoviesDataSource
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.core.mappers.mapWith
import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import io.reactivex.Single

class MoviesRemoteDataSource(
    private val flickrApi: FlickrApi,
    private val flickrPageModelMapper: ModelMapper<FlickrPhotos, PagedList<MovieImage>>
) : MoviesDataSource {
    override fun loadMovies(): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMovieImages(title: String, page: Int): Single<PagedList<MovieImage>> =
        flickrApi.searchImages(title, page, 10).flatMap {
            val moviesPage =
                it.photos?.mapWith(flickrPageModelMapper)
                    ?: PagedList.Builder<MovieImage>().build()
            Single.just(moviesPage)
        }
}