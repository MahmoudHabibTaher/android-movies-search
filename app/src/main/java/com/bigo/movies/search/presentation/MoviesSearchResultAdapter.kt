package com.bigo.movies.search.presentation

import androidx.recyclerview.widget.RecyclerView
import com.bigo.movies.R
import com.bigo.movies.core.presentation.verticalDivider
import com.bigo.movies.core.presentation.verticalLayoutManager
import com.bigo.movies.domain.entities.Movie
import com.bigo.movies.domain.entities.SearchResult
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MoviesSearchResultAdapter(
    data: MutableList<SearchResult>? = mutableListOf(),
    var onMovieSelected: (Movie) -> Unit = {}
) :
    BaseQuickAdapter<SearchResult, BaseViewHolder>(
        R.layout.layout_movies_search_result_list_item,
        data
    ) {
    override fun convert(helper: BaseViewHolder?, item: SearchResult?) {
        helper?.apply {
            item?.let { result ->
                setText(R.id.year_text_view, result.year.toString())
                getView<RecyclerView>(R.id.movies_recycler_view)?.apply {
                    val moviesAdapter = MoviesAdapter(item.movies.toMutableList(), onMovieSelected)
                    verticalLayoutManager()
                    verticalDivider(R.dimen.divider_inset)
                    adapter = moviesAdapter
                }
            }
        }
    }
}