package com.bigo.movies.domain.entities

data class MovieImage(
    val id: String,
    val url: String
) {
    class Builder {
        private var id = ""
        private var url = ""

        fun id(id: String) = apply {
            this.id = id
        }

        fun url(url: String) = apply {
            this.url = url
        }

        fun build() = MovieImage(id, url)
    }
}