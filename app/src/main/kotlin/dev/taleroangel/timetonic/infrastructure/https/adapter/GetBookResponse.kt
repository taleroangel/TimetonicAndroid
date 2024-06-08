package dev.taleroangel.timetonic.infrastructure.https.adapter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * JSON response from the getAllBooks API call
 */
@Serializable
data class GetBookResponse(
    override val req: String,
    override val status: String,
    override val errorCode: String? = null,
    override val errorMsg: String? = null,
    val allBooks: AllBooksResponse,
) : TimetonicResponseAdapter

@Serializable
data class AllBooksResponse(
    @SerialName("nbBooks") val total: Int,
    val books: List<SingleBookResponse>
)

@Serializable
data class SingleBookResponse(
    val archived: Boolean,
    val favorite: Boolean,
    val description: String? = null,
    val ownerPrefs: BookOwnerPrefsResponse
)

@Serializable
data class BookOwnerPrefsResponse(
    @SerialName("oCoverImg") val cover: String? = null,
    val title: String,
)