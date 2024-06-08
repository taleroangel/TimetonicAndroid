package dev.taleroangel.timetonic.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Representation of a book
 */
@Serializable
@Parcelize
data class Book(
    val title: String,
    val description: String,
    val coverUrl: String,
    val favorite: Boolean,
    val archived: Boolean,
) : Parcelable
