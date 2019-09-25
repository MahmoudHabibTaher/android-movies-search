package com.bigo.movies.core.domain

import com.bigo.movies.domain.core.executors.PostThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class UIThreadExecutor : PostThreadExecutor {
    override fun scheduler(): Scheduler =
        AndroidSchedulers.mainThread()
}