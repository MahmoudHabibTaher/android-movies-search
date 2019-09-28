package com.bigo.movies.search.presentation

import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bigo.movies.R
import com.bigo.movies.core.presentation.toCommaSeparated
import com.bigo.movies.domain.entities.Movie
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MoviesAdapter(
    data: MutableList<Movie>? = mutableListOf(),
    var onMovieSelected: (Movie) -> Unit = {}
) :
    BaseQuickAdapter<Movie, BaseViewHolder>(R.layout.layout_movies_list_item, data) {
    init {
        setOnItemClickListener { _, _, position ->
            getItem(position)?.apply(onMovieSelected)
        }
    }

    override fun convert(helper: BaseViewHolder?, item: Movie?) {
        helper?.apply {
            item?.let { movie ->
                setText(R.id.movie_title_text_view, movie.title)
                setText(R.id.movie_genres_text_view, movie.genres.toCommaSeparated())
                setText(R.id.movie_rating_text_view, buildRatingSpanned(movie.rating))
            }
        }
    }

    private fun buildRatingSpanned(rating: Int) = buildSpannedString {
        bold {
            append(rating.toString())
        }
        append("/5")
    }
}