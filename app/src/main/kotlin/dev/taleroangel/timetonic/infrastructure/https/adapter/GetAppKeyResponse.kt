package dev.taleroangel.timetonic.infrastructure.https.adapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JSON response returned from the getAppKey authentication
 */
@Serializable
data class GetAppKeyResponse(
    override val req: String,
    override val status: String,
    override val errorCode: String? = null,
    override val errorMsg: String? = null,
    @SerialName("appkey") val appKey: String? = null,
) : TimetonicAPIResponse