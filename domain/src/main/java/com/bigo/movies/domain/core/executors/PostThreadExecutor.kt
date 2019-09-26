package com.bigo.movies.domain.core.executors

import io.reactivex.Scheduler


interface PostThreadExecutor {
    fun scheduler(): Scheduler
}