package com.bigo.movies.domain.core.executors

import io.reactivex.Scheduler

interface ThreadExecutor {
    fun scheduler(): Scheduler
}