package com.bigo.data.movies.remote.entities

import com.google.gson.annotations.SerializedName

class FlickrPhotos(
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Long?,
    @SerializedName("perpage") val perPage: Int?,
    @SerializedName("total") val total: Long?,
    @SerializedName("photo") val photos: List<FlickrPhoto>?
)