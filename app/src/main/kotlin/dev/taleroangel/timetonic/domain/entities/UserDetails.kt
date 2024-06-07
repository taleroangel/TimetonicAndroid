package dev.taleroangel.timetonic.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val firstName: String,
    val lastName: String,
    val email: String,
    val books: Int,
) : Parcelable