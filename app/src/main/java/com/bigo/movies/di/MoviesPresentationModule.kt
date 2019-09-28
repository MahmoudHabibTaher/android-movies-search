package com.bigo.movies.di

import com.bigo.movies.details.presentation.MovieDetailsViewModel
import com.bigo.movies.search.presentation.MoviesSearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moviesPresentationModule = module {
    viewModel {
        MoviesSearchViewModel(
            androidApplication(),
            get(named("searchMoviesUseCase"))
        )
    }

    viewModel {
        MovieDetailsViewModel(
            androidApplication(),
            get(named("loadMovieImagesUseCase"))
        )
    }
}