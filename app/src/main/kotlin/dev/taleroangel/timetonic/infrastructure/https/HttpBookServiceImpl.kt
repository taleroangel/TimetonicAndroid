package dev.taleroangel.timetonic.infrastructure.https

import android.util.Log
import dev.taleroangel.timetonic.domain.entities.Book
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.service.IBookService
import dev.taleroangel.timetonic.infrastructure.https.adapter.GetBookResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.SingleBookResponse
import okhttp3.FormBody
import javax.inject.Inject

/**
 * Book requests to the Timetonic HTTP API
 */
class HttpBookServiceImpl @Inject constructor(
    /**
     * Properties for the Timetonic API like version and url
     */
    timetonicHttpProperties: TimetonicHttpProperties
) : IBookService, TimetonicHttpService(timetonicHttpProperties) {

    companion object {
        /**
         * TAG for identifying this class while logging
         */
        val TAG: String = HttpAuthServiceImpl::class.simpleName!!
    }

    init {
        Log.d(
            TAG, "Timetonic v${timetonicHttpProperties.apiVersion} " +
                    "API used for books with URL ${timetonicHttpProperties.apiUrl}"
        )
    }

    override suspend fun fetchBooks(credentials: UserCredentials): Result<List<Book>> =
        makeRequest<GetBookResponse>("getAllBooks") {
            add("o_u", credentials.authId)
            add("u_c", credentials.authId)
            add("sesskey", credentials.sessionKey)
        }.map { response: GetBookResponse ->
            response.allBooks.books.map { book: SingleBookResponse ->
                Book(
                    title = book.ownerPrefs.title,
                    description = book.description,
                    archived = book.archived,
                    favorite = book.favorite,
                    coverUrl = timetonicHttpProperties.baseUrl + book.ownerPrefs.cover
                )
            }.toList()
        }
}