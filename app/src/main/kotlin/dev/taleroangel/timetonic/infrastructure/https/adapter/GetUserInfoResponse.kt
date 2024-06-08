package dev.taleroangel.timetonic.infrastructure.https.adapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JSON response for getUserInfo API request
 */
@Serializable
data class GetUserInfoResponse(
    override val req: String,
    override val status: String,
    override val errorCode: String? = null,
    override val errorMsg: String? = null,

    val userInfo: UserInfoResponse
) : TimetonicAPIResponse

/**
 * Embedded user information inside [GetUserInfoResponse]
 */
@Serializable
data class UserInfoResponse(
    @SerialName("nbBooks") val books: Int,
    @SerialName("userPrefs") val preferences: UserInfoPreferencesResponse,
)

/**
 * Embedded user preferences inside [UserInfoResponse]
 */
@Serializable
data class UserInfoPreferencesResponse(
    @SerialName("mail1") val mail: String,
    @SerialName("fname") val firstName: String,
    @SerialName("lname") val lastName: String,
)