package dev.taleroangel.timetonic.infrastructure.https

import android.util.Log
import arrow.core.Either
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.infrastructure.exception.ExpiredTokenException
import dev.taleroangel.timetonic.domain.service.IAuthService
import okhttp3.OkHttpClient
import javax.inject.Inject

class HttpAuthServiceImpl @Inject constructor(
    timetonicHttpProperties: TimetonicHttpProperties
) : IAuthService {

    companion object {
        val TAG: String = HttpAuthServiceImpl::class.simpleName!!
    }

    init {
        Log.d(
            TAG, "Timetonic v${timetonicHttpProperties.apiVersion} " +
                    "API used for authentication with URL ${timetonicHttpProperties.apiUrl}"
        )
    }

    /**
     * HTTP client for making requests
     */
    private val client: OkHttpClient = OkHttpClient()

    override suspend fun key(): Result<ApplicationKey> {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(
        email: String,
        password: String,
        appKey: ApplicationKey
    ): Result<UserCredentials> {
        TODO("Not yet implemented")
    }

    override suspend fun user(credentials: UserCredentials): Result<UserDetails> {
        TODO("Not yet implemented")
    }
}