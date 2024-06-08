package dev.taleroangel.timetonic.infrastructure.https

/**
 * Get specific properties for timetonic services
 */
data class TimetonicHttpProperties(
    val baseUrl: String,
    val apiRoute: String,
    val apiVersion: String,
) {
    /**
     * Get the complete path to the API
     */
    val apiUrl: String
        get() = baseUrl + apiRoute
}