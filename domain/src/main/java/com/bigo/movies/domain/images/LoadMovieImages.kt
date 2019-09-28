package com.bigo.movies.domain.images

import com.bigo.movies.domain.MoviesDataSource
import com.bigo.movies.domain.core.executors.PostThreadExecutor
import com.bigo.movies.domain.core.executors.ThreadExecutor
import com.bigo.movies.domain.core.usecase.UseCase
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import io.reactivex.Single

class LoadMovieImages(
    threadExecutor: ThreadExecutor,
    postThreadExecutor: PostThreadExecutor,
    private val moviesDataSource: MoviesDataSource
) : UseCase<PagedList<MovieImage>, LoadMoviesImagesParams>(threadExecutor, postThreadExecutor) {
    override fun buildUseCase(args: LoadMoviesImagesParams?): Single<PagedList<MovieImage>> =
        moviesDataSource.loadMovieImages(args?.title ?: "", args?.page ?: 1)
}