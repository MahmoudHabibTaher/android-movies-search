package com.bigo.movies.entities

import android.os.Parcelable
import com.bigo.movies.domain.entities.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieParcelable(
    val title: String,
    val year: Int,
    val cast: List<ActorParcelable>,
    val genres: List<GenreParcelable>,
    val rating: Int
) : Parcelable {
    companion object {
        fun fromMovie(movie: Movie) =
            MovieParcelable(
                movie.title,
                movie.year,
                movie.cast.map {
                    ActorParcelable.fromActor(it)
                },
                movie.genres.map {
                    GenreParcelable.fromGenre(it)
                },
                movie.rating
            )
    }
}