package dev.taleroangel.timetonic.infrastructure.mock

import arrow.core.Either
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException
import dev.taleroangel.timetonic.domain.service.IAuthService
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Emulate an auth service for testing purposes
 */
class MockAuthServiceImpl @Inject constructor() : IAuthService {
    override suspend fun key(): Result<ApplicationKey> {
        delay(1000)
        return Result.success(ApplicationKey("application-key"))
    }

    override suspend fun authenticate(
        email: String,
        password: String
    ): Result<UserCredentials> {
        delay(2000)
        return Result.success(UserCredentials("authid-mock", "oauthkey-mock"))
    }
}