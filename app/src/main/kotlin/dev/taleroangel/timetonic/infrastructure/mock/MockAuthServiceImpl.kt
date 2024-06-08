package dev.taleroangel.timetonic.infrastructure.mock

import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
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
        password: String,
        appKey: ApplicationKey,
    ): Result<UserCredentials> {
        delay(1000)
        return Result.success(UserCredentials("authid-mock", "auth-key-mock", "session-key-mock"))
    }

    override suspend fun user(credentials: UserCredentials): Result<UserDetails> {
        delay(1000)
        return Result.success(UserDetails("John", "Doe", "john_doe@email.com", 4))
    }
}