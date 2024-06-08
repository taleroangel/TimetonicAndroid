package dev.taleroangel.timetonic.infrastructure.https

import android.util.Log
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.infrastructure.https.adapter.CreateOAuthKeyResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.CreateSessKey
import dev.taleroangel.timetonic.infrastructure.https.adapter.GetAppKeyResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.GetUserInfoResponse
import javax.inject.Inject

/**
 * Authentication requests to the Timetonic HTTP API
 */
class HttpAuthServiceImpl @Inject constructor(
    /**
     * Properties for the Timetonic API like version and url
     */
    timetonicHttpProperties: TimetonicHttpProperties
) : IAuthService, TimetonicHttpService(timetonicHttpProperties) {

    companion object {
        /**
         * TAG for identifying this class while logging
         */
        val TAG: String = HttpAuthServiceImpl::class.simpleName!!

        /**
         * Application name used to get the appKey
         */
        val APP_NAME: String = HttpAuthServiceImpl::class.java.`package`!!.name
    }

    init {
        Log.d(
            TAG, "Timetonic v${timetonicHttpProperties.apiVersion} " +
                    "API used for authentication with URL ${timetonicHttpProperties.apiUrl}"
        )
    }

    /**
     * Obtain an application key
     */
    override suspend fun key(): Result<ApplicationKey> = makeRequest<GetAppKeyResponse>(
        "createAppkey"
    ) {
        add("appname", APP_NAME)
    }.map { value: GetAppKeyResponse -> ApplicationKey(value.appKey!!) }

    /**
     * Get the OAuth & Session
     */
    override suspend fun authenticate(
        email: String,
        password: String,
        appKey: ApplicationKey
    ): Result<UserCredentials> {
        // 1. Get the OAuthKey
        val oAuthResponse = makeRequest<CreateOAuthKeyResponse>(
            "createOauthkey"
        ) {
            add("login", email)
            add("pwd", password)
            add("appkey", appKey.value)
        }.getOrElse { return Result.failure(it) }

        // 2. Create Session Key
        val sessionKey = makeRequest<CreateSessKey>("createSesskey") {
            add("oauthkey", oAuthResponse.oAuthKey)
            add("o_u", oAuthResponse.ou)
            add("u_c", oAuthResponse.ou)
        }.getOrElse { return Result.failure(it) }

        // Return the success
        return Result.success(
            UserCredentials(
                authKey = oAuthResponse.oAuthKey,
                authId = oAuthResponse.ou,
                sessionKey = sessionKey.sessKey,
            )
        )
    }

    /**
     * Get user details
     */
    override suspend fun user(credentials: UserCredentials): Result<UserDetails> =
        makeRequest<GetUserInfoResponse>("getUserInfo") {
            add("sesskey", credentials.sessionKey)
            add("o_u", credentials.authId)
            add("u_c", credentials.authId)
        }.map { value: GetUserInfoResponse ->
            UserDetails(
                email = value.userInfo.preferences.mail,
                firstName = value.userInfo.preferences.firstName,
                lastName = value.userInfo.preferences.lastName,
                books = value.userInfo.books,
            )
        }
}