package com.bigo.movies.domain.entities

data class Movie(
    val title: String,
    val year: Int,
    val cast: List<Actor>,
    val genres: List<Genre>,
    val rating: Int
) {
    class Builder {
        private var title = ""
        private var year = 0
        private var cast = listOf<Actor>()
        private var genres = listOf<Genre>()
        private var rating = 0

        fun title(title: String) = apply {
            this.title = title
        }

        fun year(year: Int) = apply {
            this.year = year
        }

        fun cast(cast: List<Actor>) = apply {
            this.cast = cast
        }

        fun genres(genres: List<Genre>) = apply {
            this.genres = genres
        }

        fun rating(rating: Int) = apply {
            this.rating = rating
        }

        fun build() =
            Movie(title, year, cast, genres, rating)
    }
}