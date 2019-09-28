package com.bigo.movies.details.presentation

import android.widget.ImageView
import com.bigo.movies.R
import com.bigo.movies.core.glide.GlideApp
import com.bigo.movies.domain.entities.MovieImage
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MovieImagesAdapter(
    data: MutableList<MovieImage>? = mutableListOf()
) :
    BaseQuickAdapter<MovieImage, BaseViewHolder>(R.layout.layout_movie_images_grid_item, data) {
    override fun convert(helper: BaseViewHolder?, item: MovieImage?) {
        helper?.apply {
            item?.let { image ->
                getView<ImageView>(R.id.image_view)?.apply {
                    GlideApp.with(this)
                        .load(image.url)
                        .centerCrop()
                        .into(this)
                }
            }
        }
    }
}