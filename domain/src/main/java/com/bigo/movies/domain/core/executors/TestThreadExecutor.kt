package com.bigo.movies.domain.core.executors

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestThreadExecutor : ThreadExecutor {
    override fun scheduler(): Scheduler = Schedulers.single()
}