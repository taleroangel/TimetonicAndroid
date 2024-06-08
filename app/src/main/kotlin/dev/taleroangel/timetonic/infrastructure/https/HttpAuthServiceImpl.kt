package dev.taleroangel.timetonic.infrastructure.https

import android.util.Log
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.infrastructure.exception.BadRequest
import dev.taleroangel.timetonic.infrastructure.exception.GenericFailedRequest
import dev.taleroangel.timetonic.infrastructure.exception.UnexpectedResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.CreateOAuthKeyResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.CreateSessKey
import dev.taleroangel.timetonic.infrastructure.https.adapter.GetAppKeyResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.GetUserInfoResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.TimetonicAPIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

/**
 * Authentication requests to the Timetonic HTTP API
 */
class HttpAuthServiceImpl @Inject constructor(
    /**
     * Properties for the Timetonic API like version and url
     */
    private val timetonicHttpProperties: TimetonicHttpProperties
) : IAuthService {

    companion object {
        val TAG: String = HttpAuthServiceImpl::class.simpleName!!
        val APP_NAME: String = HttpAuthServiceImpl::class.java.`package`!!.name
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

    /**
     * Decode JSON ignoring unknown keys
     */
    private val jsonDecoder = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Make an HTTP POST request to the API with the given body
     */
    private suspend inline fun <reified T : TimetonicAPIResponse> makeRequest(body: FormBody): Result<T> =
        // Create the HTTP response
        withContext(Dispatchers.IO) {
            // POST request to the API
            val request = Request.Builder()
                .url(timetonicHttpProperties.apiUrl)
                .post(body)
                .build()

            // Create the HTTP call
            client.newCall(request).execute().use { response ->

                // Print the response
                Log.d(TAG, "Got response: $response")

                // Return the error
                if (!response.isSuccessful) {
                    return@withContext Result.failure(GenericFailedRequest(response.message))
                }

                // Check if HTML, specific to timetonic API
                if (response.headers["content-type"] != "application/json") {
                    return@withContext Result.failure(BadRequest())
                }

                val bodyString = response.body!!.string()

                try {

                    // Decode the response
                    val parsedResponse: T = jsonDecoder.decodeFromString(bodyString)

                    // Show error if present
                    if (parsedResponse.isFailure()) {
                        return@withContext Result.failure(GenericFailedRequest(parsedResponse.errorMsg!!))
                    }

                    // Return value
                    return@withContext Result.success(parsedResponse)

                } catch (e: SerializationException) {
                    return@withContext Result.failure(UnexpectedResponse(bodyString, e))
                }
            }
        }

    /**
     * Obtain an application key
     */
    override suspend fun key(): Result<ApplicationKey> = makeRequest<GetAppKeyResponse>(
        FormBody.Builder()
            .add("req", "createAppkey")
            .add("version", timetonicHttpProperties.apiVersion)
            .add("appname", APP_NAME)
            .build()
    ).map { value: GetAppKeyResponse -> ApplicationKey(value.appKey!!) }

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
            FormBody.Builder()
                .add("req", "createOauthkey")
                .add("version", timetonicHttpProperties.apiVersion)
                .add("login", email)
                .add("pwd", password)
                .add("appkey", appKey.value)
                .build()
        ).getOrElse { return Result.failure(it) }

        // 2. Create Session Key
        val sessionKey = makeRequest<CreateSessKey>(
            FormBody.Builder()
                .add("req", "createSesskey")
                .add("version", timetonicHttpProperties.apiVersion)
                .add("oauthkey", oAuthResponse.oAuthKey)
                .add("o_u", oAuthResponse.ou)
                .add("u_c", oAuthResponse.ou)
                .build()
        ).getOrElse { return Result.failure(it) }

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
        makeRequest<GetUserInfoResponse>(
            FormBody.Builder()
                .add("req", "getUserInfo")
                .add("version", timetonicHttpProperties.apiVersion)
                .add("sesskey", credentials.sessionKey)
                .add("o_u", credentials.authId)
                .add("u_c", credentials.authId)
                .build()
        ).map { value: GetUserInfoResponse ->
            UserDetails(
                email = value.userInfo.preferences.mail,
                firstName = value.userInfo.preferences.firstName,
                lastName = value.userInfo.preferences.lastName,
                books = value.userInfo.books,
            )
        }
}