package com.bigo.movies.domain.core.executors

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class TestPostThreadExecutor : PostThreadExecutor {
    override fun scheduler(): Scheduler = Schedulers.single()
}