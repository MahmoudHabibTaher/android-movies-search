package com.bigo.movies.search.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bigo.movies.core.presentation.BaseViewModel
import com.bigo.movies.domain.core.log.logError
import com.bigo.movies.domain.core.usecase.UseCase
import com.bigo.movies.domain.entities.SearchResult
import com.bigo.movies.domain.search.SearchMoviesParams

class MoviesSearchViewModel(
    application: Application,
    private val searchMovies: UseCase<List<SearchResult>, SearchMoviesParams>
) : BaseViewModel(application) {

    val searchResultsLiveData = MutableLiveData<List<SearchResult>>()

    fun searchMovies(query: String) {
        val params = SearchMoviesParams(query)
        val single = searchMovies.execute(params)
        execute(single, onSuccess = {
            notifySearchResults(it)
        }, onError = {
            logError("Error searching movies", it)
        })
    }

    private fun notifySearchResults(results: List<SearchResult>) {
        searchResultsLiveData.postValue(results)
    }
}