package dev.taleroangel.timetonic.domain.repository

import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import kotlinx.coroutines.flow.Flow

/**
 * Manipulate remembered auth data
 */
interface IAuthRepository {

    /**
     * Get the [ApplicationKey] stored if present
     */
    val applicationKey: Flow<ApplicationKey?>

    /**
     * Store a new [ApplicationKey] (or delete it)
     */
    suspend fun storeApplicationKey(applicationKey: ApplicationKey?)

    /**
     * Get the [UserCredentials] stored if present
     */
    val credentials: Flow<UserCredentials?>

    /**
     * Store a new [UserCredentials] (or delete it)
     */
    suspend fun storeCredentials(userCredentials: UserCredentials?)
}