package dev.taleroangel.timetonic.infrastructure.exception

/**
 * Root for the custom exceptions in the application
 */
sealed class TimetonicException(reason: String) : Exception(reason)