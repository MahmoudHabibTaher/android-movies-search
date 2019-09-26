package com.bigo.movies.di

import com.bigo.data.file.AssetFileReader
import com.bigo.data.file.FileReader
import com.bigo.data.movies.MoviesRepository
import com.bigo.data.movies.local.MoviesLocalDataSource
import com.bigo.data.movies.local.entities.MovieLocal
import com.bigo.data.movies.local.mappers.MovieLocalModelMapper
import com.bigo.data.parsers.JsonParser
import com.bigo.data.parsers.JsonParserImpl
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.search.MoviesDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val moviesDataModule = module {
    single(named("moviesLocalDataSource")) {
        MoviesLocalDataSource(get(), get(), get()) as MoviesDataSource
    }

    single(named("moviesRepository")) {
        MoviesRepository(get(named("moviesLocalDataSource"))) as MoviesDataSource
    }

    single {
        AssetFileReader(androidApplication()) as FileReader
    }

    single {
        JsonParserImpl(get()) as JsonParser
    }

    single {
        MovieLocalModelMapper() as ModelMapper<MovieLocal, Movie>
    }

    single {
        GsonBuilder().create() as Gson
    }
}