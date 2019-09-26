package com.bigo.movies.domain.core.executors

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class TestThreadExecutor : ThreadExecutor {
    override fun scheduler(): Scheduler = Schedulers.single()
}