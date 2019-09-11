package com.bigo.moviessearch.domain.core.executors

import io.reactivex.rxjava3.core.Scheduler

interface ThreadExecutor {
    fun scheduler(): Scheduler
}