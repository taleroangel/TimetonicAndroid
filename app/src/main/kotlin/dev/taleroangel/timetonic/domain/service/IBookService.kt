package dev.taleroangel.timetonic.domain.service

import dev.taleroangel.timetonic.domain.entities.Book
import dev.taleroangel.timetonic.domain.entities.UserCredentials

/**
 * Fetch information about books
 */
interface IBookService {
    /**
     * Get all the books
     */
    suspend fun fetchBooks(credentials: UserCredentials): Result<List<Book>>
}