package dev.taleroangel.timetonic.domain.service

import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails

/**
 * Authenticate users, gather and manipulate user data and credentials
 */
interface IAuthService {

    /**
     * Gather a new API key
     */
    suspend fun key(): Result<ApplicationKey>

    /**
     * Authenticate the user and gather the credentials for future requests
     */
    suspend fun authenticate(
        email: String,
        password: String,
        appKey: ApplicationKey
    ): Result<UserCredentials>

    /**
     * Get the user details like email and name
     */
    suspend fun user(credentials: UserCredentials): Result<UserDetails>
}