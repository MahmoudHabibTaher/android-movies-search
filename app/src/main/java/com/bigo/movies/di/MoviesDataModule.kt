package com.bigo.movies.di

import com.bigo.data.core.retrofit.RetrofitBuilder
import com.bigo.data.core.retrofit.ServiceHelper
import com.bigo.data.file.AssetFileReader
import com.bigo.data.file.FileReader
import com.bigo.data.movies.MoviesRepository
import com.bigo.data.movies.local.MoviesLocalDataSource
import com.bigo.data.movies.local.entities.MovieLocal
import com.bigo.data.movies.local.mappers.MovieLocalModelMapper
import com.bigo.data.movies.remote.FlickrApi
import com.bigo.data.movies.remote.MoviesRemoteDataSource
import com.bigo.data.movies.remote.entities.FlickrPhotos
import com.bigo.data.movies.remote.mappers.FlickrPageModelMapper
import com.bigo.data.parsers.JsonParser
import com.bigo.data.parsers.JsonParserImpl
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.MoviesDataSource
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesDataModule = module {
    single(named("moviesLocalDataSource")) {
        MoviesLocalDataSource(get(), get(), get(named("movieLocalModelMapper"))) as MoviesDataSource
    }

    single(named("moviesRemoteDataSource")) {
        MoviesRemoteDataSource(get(), get(named("flickrPageModelMapper"))) as MoviesDataSource
    }

    single(named("moviesRepository")) {
        MoviesRepository(
            get(named("moviesLocalDataSource")),
            get(named("moviesRemoteDataSource"))
        ) as MoviesDataSource
    }

    single {
        AssetFileReader(androidApplication()) as FileReader
    }

    single {
        JsonParserImpl(get()) as JsonParser
    }

    factory(named("movieLocalModelMapper")) {
        MovieLocalModelMapper() as ModelMapper<MovieLocal, Movie>
    }

    single {
        GsonBuilder().create() as Gson
    }

    factory {
        ServiceHelper.createService(get(named("flickrRetrofit"))) as FlickrApi
    }

    single(named("flickrRetrofit")) {
        RetrofitBuilder.buildFlickrRetrofit() as Retrofit
    }

    factory(named("flickrPageModelMapper")) {
        FlickrPageModelMapper() as ModelMapper<FlickrPhotos, PagedList<MovieImage>>
    }
}