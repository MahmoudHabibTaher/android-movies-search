package com.bigo.moviessearch.domain.core.executors

import io.reactivex.rxjava3.core.Scheduler

interface PostThreadExecutor {
    fun scheduler(): Scheduler
}