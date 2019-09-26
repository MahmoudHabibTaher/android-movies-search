package com.bigo.movies.core.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val loadingVisibleLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    protected fun <T> execute(
        single: Single<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val disposable = single.subscribe(onSuccess, onError)
        disposables.add(disposable)
    }

    protected fun notfiyLoadingVisible(visible: Boolean) {
        loadingVisibleLiveData.postValue(visible)
    }

    protected fun notifyError(error: String) {
        errorLiveData.postValue(error)
    }

    override fun onCleared() {
        super.onCleared()

        disposables.dispose()
    }
}