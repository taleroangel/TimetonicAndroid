package dev.taleroangel.timetonic.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Store API user credentials
 */
@Parcelize
data class UserCredentials(
    val authId: String,
    val authKey: String,
    val sessionKey: String,
): Parcelable
