package com.bigo.movies.core.domain

import com.bigo.movies.domain.core.executors.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class BackgroundThreadExecutor : ThreadExecutor {
    override fun scheduler(): Scheduler =
        Schedulers.io()
}