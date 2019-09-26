package com.bigo.data.movies.local.entities

import com.google.gson.annotations.SerializedName

data class MovieLocal(
    @SerializedName("title") val title: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("cast") val cast: List<String>?,
    @SerializedName("genres") val genres: List<String>?,
    @SerializedName("rating") val rating: Int?
) {
    class Builder {
        private var title: String? = ""
        private var year: Int? = 0
        private var cast: List<String>? = listOf()
        private var genres: List<String>? = listOf()
        private var rating: Int? = 0

        fun title(title: String?) = apply {
            this.title = title
        }

        fun year(year: Int?) = apply {
            this.year = year
        }

        fun cast(cast: List<String>?) = apply {
            this.cast = cast
        }

        fun genres(genres: List<String>?) = apply {
            this.genres = genres
        }

        fun rating(rating: Int?) = apply {
            this.rating = rating
        }

        fun build() =
            MovieLocal(title, year, cast, genres, rating)
    }
}