package com.bigo.movies.domain.core.executors

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestPostThreadExecutor : PostThreadExecutor {
    override fun scheduler(): Scheduler = Schedulers.single()
}