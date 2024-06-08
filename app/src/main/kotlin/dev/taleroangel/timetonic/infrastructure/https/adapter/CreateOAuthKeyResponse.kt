package dev.taleroangel.timetonic.infrastructure.https.adapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOAuthKeyResponse(
    override val req: String,
    override val status: String,
    override val errorMsg: String? = null,
    override val errorCode: String? = null,
    @SerialName("oauthkey") val oAuthKey: String,
    @SerialName("o_u") val ou: String,
) : TimetonicAPIResponse
