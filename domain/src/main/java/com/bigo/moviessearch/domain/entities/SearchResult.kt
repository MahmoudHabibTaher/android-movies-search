package com.bigo.moviessearch.domain.entities

data class SearchResult(
    val year: Int,
    val movies: List<Movie>
) {
    class Builder {
        private var year = 0
        private var movies = listOf<Movie>()

        fun year(year: Int) = apply {
            this.year = year
        }

        fun movies(movies: List<Movie>) = apply {
            this.movies = movies
        }

        fun build() =
            SearchResult(year, movies)
    }
}