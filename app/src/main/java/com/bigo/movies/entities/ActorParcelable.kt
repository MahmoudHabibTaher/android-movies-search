package com.bigo.movies.entities

import android.os.Parcelable
import com.bigo.movies.domain.entities.Actor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActorParcelable(val name: String) : Parcelable {
    companion object {
        fun fromActor(actor: Actor) =
            ActorParcelable(actor.name)
    }

    override fun toString(): String = name
}