package dev.taleroangel.timetonic.domain.service

import arrow.core.Either
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException

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
        password: String
    ): Result<UserCredentials>
}