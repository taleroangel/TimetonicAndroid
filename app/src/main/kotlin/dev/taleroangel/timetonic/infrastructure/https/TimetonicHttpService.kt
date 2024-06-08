package dev.taleroangel.timetonic.infrastructure.https

import android.util.Log
import dev.taleroangel.timetonic.infrastructure.exception.BadRequest
import dev.taleroangel.timetonic.infrastructure.exception.GenericFailedRequest
import dev.taleroangel.timetonic.infrastructure.exception.UnexpectedResponse
import dev.taleroangel.timetonic.infrastructure.https.adapter.TimetonicResponseAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Make HTTP requests to the Timetonic public API
 */
abstract class TimetonicHttpService(
    /**
     * Properties for the Timetonic API like version and url
     */
    protected val timetonicHttpProperties: TimetonicHttpProperties,
) {
    /**
     * HTTP client for making requests
     */
    protected val client: OkHttpClient = OkHttpClient()

    /**
     * Decode JSON ignoring unknown keys
     */
    protected val jsonDecoder = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Make an HTTP POST request to the API with the given body
     */
    protected suspend inline fun <reified T : TimetonicResponseAdapter> makeRequest(
        endpoint: String,
        crossinline bodyBuilder: FormBody.Builder.() -> Unit,
    ): Result<T> =
        // Create the HTTP response
        withContext(Dispatchers.IO) {
            // POST request to the API
            val request = Request.Builder()
                .url(timetonicHttpProperties.apiUrl)
                .post(
                    FormBody.Builder()
                        .add("req", endpoint)
                        .add("version", timetonicHttpProperties.apiVersion)
                        .apply { bodyBuilder.invoke(this) }
                        .build()
                )
                .build()

            // Create the HTTP call
            client.newCall(request).execute().use { response ->

                // Print the response
                Log.d(HttpAuthServiceImpl.TAG, "Got response: $response")

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

}