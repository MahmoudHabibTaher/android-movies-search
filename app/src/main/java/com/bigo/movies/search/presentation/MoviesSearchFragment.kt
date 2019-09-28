package com.bigo.movies.search.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bigo.movies.R
import com.bigo.movies.core.presentation.BaseFragment
import com.bigo.movies.core.presentation.verticalDivider
import com.bigo.movies.core.presentation.verticalLayoutManager
import com.bigo.movies.domain.entities.SearchResult
import kotlinx.android.synthetic.main.fragment_movies_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesSearchFragment : BaseFragment() {
    private val moviesSearchViewModel: MoviesSearchViewModel by viewModel()

    private val searchResultsObserver = Observer<List<SearchResult>> {
        showSearchResults(it)
    }

    private val resultsAdapter = MoviesSearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingVisible(moviesSearchViewModel, this)
        observeError(moviesSearchViewModel, this)
        observeSearchResults(moviesSearchViewModel, this)
    }

    override val layoutRes: Int
        get() = R.layout.fragment_movies_search

    override fun initView(view: View) {
        search_text_input_edit_text.doOnTextChanged { text, _, _, _ ->
            moviesSearchViewModel.searchMovies(text?.toString() ?: "")
        }

        search_results_recycler_view.apply {
            verticalLayoutManager()
            adapter = resultsAdapter
            verticalDivider()
        }
    }

    override fun setLoadingVisible(visible: Boolean) {
        loading_indicator_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun observeSearchResults(viewModel: MoviesSearchViewModel, owner: LifecycleOwner) {
        viewModel.searchResultsLiveData.observe(owner, searchResultsObserver)
    }

    private fun showSearchResults(results: List<SearchResult>) {
        resultsAdapter.setNewData(results)
    }
}