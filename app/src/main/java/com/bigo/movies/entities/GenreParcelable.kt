package com.bigo.movies.entities

import android.os.Parcelable
import com.bigo.movies.domain.entities.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreParcelable(val name: String) : Parcelable {
    companion object {
        fun fromGenre(genre: Genre) =
            GenreParcelable(genre.name)
    }

    override fun toString(): String = name
}