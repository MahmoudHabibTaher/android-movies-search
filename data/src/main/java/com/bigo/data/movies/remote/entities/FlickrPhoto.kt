package com.bigo.data.movies.remote.entities

import com.google.gson.annotations.SerializedName

class FlickrPhoto(
    @SerializedName("id") val id: String?,
    @SerializedName("secret") val secret: String?,
    @SerializedName("server") val server: String?,
    @SerializedName("farm") val farm: String?
)