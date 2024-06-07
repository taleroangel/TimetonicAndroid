package dev.taleroangel.timetonic.infrastructure.https

/**
 * Get specific properties for timetonic services
 */
data class TimetonicHttpProperties(
    val apiUrl: String,
    val apiVersion: String,
)