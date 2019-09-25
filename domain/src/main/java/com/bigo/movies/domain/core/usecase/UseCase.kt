package com.bigo.movies.domain.core.usecase

import com.bigo.movies.domain.core.executors.PostThreadExecutor
import com.bigo.movies.domain.core.executors.ThreadExecutor
import io.reactivex.rxjava3.core.Single

abstract class UseCase<T, V>(
    private val threadExecutor: ThreadExecutor,
    private val postThreadExecutor: PostThreadExecutor
) {
    protected abstract fun buildUseCase(args: V?): Single<T>

    fun execute(args: V? = null): Single<T> =
        buildUseCase(args)
            .subscribeOn(threadExecutor.scheduler())
            .observeOn(postThreadExecutor.scheduler())
}