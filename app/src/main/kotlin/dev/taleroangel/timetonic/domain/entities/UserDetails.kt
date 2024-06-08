package dev.taleroangel.timetonic.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserDetails(
    val firstName: String,
    val lastName: String,
    val email: String,
    val books: Int,
) : Parcelable {

    /**
     * Get the name concatenated
     */
    val name: String
        get() = "$firstName $lastName"
}