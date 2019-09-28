package com.bigo.movies.di

import com.bigo.movies.domain.core.usecase.UseCase
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import com.bigo.movies.domain.entities.SearchResult
import com.bigo.movies.domain.images.LoadMovieImages
import com.bigo.movies.domain.images.LoadMoviesImagesParams
import com.bigo.movies.domain.search.SearchMovies
import com.bigo.movies.domain.search.SearchMoviesParams
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moviesDomainModule = module {
    factory(named("searchMoviesUseCase")) {
        SearchMovies(
            get(),
            get(),
            get(named("moviesRepository"))
        ) as UseCase<List<SearchResult>, SearchMoviesParams>
    }

    factory(named("loadMovieImagesUseCase")) {
        LoadMovieImages(
            get(),
            get(),
            get(named("moviesRepository"))
        ) as UseCase<PagedList<MovieImage>, LoadMoviesImagesParams>
    }
}