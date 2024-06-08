package dev.taleroangel.timetonic.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Store API user credentials
 */
@Parcelize
@Serializable
data class UserCredentials(
    val authId: String,
    val authKey: String,
    val sessionKey: String,
): Parcelable
