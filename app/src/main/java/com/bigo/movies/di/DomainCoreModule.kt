package com.bigo.movies.di

import com.bigo.movies.core.domain.BackgroundThreadExecutor
import com.bigo.movies.core.domain.UIThreadExecutor
import com.bigo.movies.domain.core.executors.PostThreadExecutor
import com.bigo.movies.domain.core.executors.ThreadExecutor
import org.koin.dsl.module

val domainCoreModule = module {
    single {
        BackgroundThreadExecutor() as ThreadExecutor
    }

    single {
        UIThreadExecutor() as PostThreadExecutor
    }
}