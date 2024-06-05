package dev.taleroangel.timetonic.infrastructure.https

import arrow.core.Either
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException
import dev.taleroangel.timetonic.domain.service.IAuthService
import javax.inject.Inject

class HttpAuthServiceImpl @Inject constructor() : IAuthService {
    override suspend fun key(): Result<ApplicationKey> {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(email: String, password: String): Result<UserCredentials> {
        TODO("Not yet implemented")
    }
}