package dev.taleroangel.timetonic.presentation.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.service.IBookService
import dev.taleroangel.timetonic.presentation.ui.state.BooksViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Manage books in application
 */
@HiltViewModel
class BooksViewModel @Inject constructor(
    /**
     * Service for requesting books
     */
    private val booksService: IBookService,

    /**
     * Keep state across configuration changes
     */
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        /**
         * TAG for using with [Log] and [SavedStateHandle]
         */
        val TAG: String = BooksViewModel::class.simpleName!!
    }

    /**
     * Current state of the books view
     */
    private val _booksViewState: MutableLiveData<BooksViewState> =
        savedStateHandle.getLiveData("$TAG:booksViewState", BooksViewState.Loading)

    /**
     * Current state of the books view (immutable)
     */
    val booksViewState: LiveData<BooksViewState>
        get() = _booksViewState

    /**
     * Refresh the list of books
     */
    fun refresh(credentials: UserCredentials) {
        // Set the loading state
        _booksViewState.value = BooksViewState.Loading

        // Try and get the books
        viewModelScope.launch {
            try {

                booksService.fetchBooks(credentials).fold(
                    { books ->
                        // Set the content
                        _booksViewState.value = BooksViewState.Content(books)
                    },
                    { err ->
                        // Set the error state
                        _booksViewState.value = BooksViewState.Error(err)
                    })

            } catch (e: Throwable) {
                // Set the error state
                _booksViewState.value = BooksViewState.Error(e)
            }
        }
    }
}