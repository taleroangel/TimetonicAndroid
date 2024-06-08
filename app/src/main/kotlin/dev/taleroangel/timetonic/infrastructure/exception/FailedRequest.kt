package dev.taleroangel.timetonic.infrastructure.exception

/**
 * Timetonic API request failed
 */
sealed class FailedRequest(what: String) : TimetonicException("FailedRequest: $what")