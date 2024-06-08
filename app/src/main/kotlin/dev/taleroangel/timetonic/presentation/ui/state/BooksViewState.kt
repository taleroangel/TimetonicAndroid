package dev.taleroangel.timetonic.presentation.ui.state

import android.os.Parcelable
import dev.taleroangel.timetonic.domain.entities.Book
import kotlinx.parcelize.Parcelize

/**
 * Represent the books view state
 */
@Parcelize
sealed class BooksViewState : Parcelable {
    /**
     * Book list is loading
     */
    data object Loading : BooksViewState()

    /**
     * An error occurred during fetching, the error is shown stored as [err]
     */
    data class Error(val err: Throwable) : BooksViewState()

    /**
     * Book content successfully stored within [books]
     */
    data class Content(val books: List<Book>) : BooksViewState()
}