package com.bigo.data.movies.remote.mappers

import com.bigo.data.movies.remote.FlickrApi
import com.bigo.data.movies.remote.entities.FlickrPhoto
import com.bigo.data.movies.remote.entities.FlickrPhotos
import com.bigo.movies.domain.core.mappers.ModelMapper
import com.bigo.movies.domain.entities.MovieImage
import com.bigo.movies.domain.entities.PagedList

class FlickrPageModelMapper : ModelMapper<FlickrPhotos, PagedList<MovieImage>> {
    override fun map(model: FlickrPhotos): PagedList<MovieImage> =
        PagedList.Builder<MovieImage>().run {
            page(model.page ?: 0)
            perPage(model.perPage ?: 0)
            pages(model.pages ?: 0)
            total(model.total ?: 0)
            items(model.photos?.map(::mapPhoto) ?: listOf())
            build()
        }

    private fun mapPhoto(photo: FlickrPhoto): MovieImage =
        MovieImage.Builder().run {
            id(photo.id ?: "")
            url(buildImageUrl(photo))
            build()
        }

    private fun buildImageUrl(photo: FlickrPhoto) =
        photo.run {
            FlickrApi.buildUrl(
                photo.farm ?: "",
                photo.server ?: "",
                photo.id ?: "",
                photo.secret ?: ""
            )
        }
}