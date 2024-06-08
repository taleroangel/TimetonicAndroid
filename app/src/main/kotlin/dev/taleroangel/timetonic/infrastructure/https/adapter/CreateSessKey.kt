package dev.taleroangel.timetonic.infrastructure.https.adapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JSON response representation of createSesskey request
 */
@Serializable
data class CreateSessKey(
    override val req: String,
    override val status: String,
    override val errorCode: String? = null,
    override val errorMsg: String? = null,
    @SerialName("sesskey") val sessKey: String,
): TimetonicResponseAdapter