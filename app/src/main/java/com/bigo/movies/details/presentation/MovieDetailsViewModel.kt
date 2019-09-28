package com.bigo.movies.details.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bigo.movies.core.presentation.BaseViewModel
import com.bigo.movies.domain.core.log.logError
import com.bigo.movies.domain.core.usecase.UseCase
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList
import com.bigo.movies.domain.images.LoadMoviesImagesParams

class MovieDetailsViewModel(
    application: Application,
    private val loadMovieImages: UseCase<PagedList<MovieImage>, LoadMoviesImagesParams>
) : BaseViewModel(application) {
    val imagesLiveData = MutableLiveData<List<MovieImage>>()
    val moreImagesLiveData = MutableLiveData<List<MovieImage>>()
    val moreImagesAvailableLiveData = MutableLiveData<Boolean>()

    private var title = ""

    private var lastPage: PagedList<MovieImage>? = null

    fun loadImages(title: String, page: Int = 1) {
        this.title = title

        notifyLoadingVisible(true)
        val params = LoadMoviesImagesParams(title, page)
        val single = loadMovieImages.execute(params)

        execute(single, onSuccess = {
            if (it.isFirstPage()) {
                notifyImages(it.items)
            } else {
                notifyMoreImages(it.items)
            }

            notifyMoreImagesAvailable(it.hasNext())

            lastPage = it
            notifyLoadingVisible(false)
        }, onError = {
            logError("Error loading movie images", it)
            notifyLoadingVisible(false)
        })
    }

    fun loadMoreImages() {
        lastPage?.let { pagedList ->
            if (pagedList.hasNext()) {
                loadImages(title, pagedList.nextPage())
            }
        }
    }

    private fun notifyImages(images: List<MovieImage>) {
        imagesLiveData.postValue(images)
    }

    private fun notifyMoreImages(images: List<MovieImage>) {
        moreImagesLiveData.postValue(images)
    }

    private fun notifyMoreImagesAvailable(available: Boolean) {
        moreImagesAvailableLiveData.postValue(available)
    }
}