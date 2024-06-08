package dev.taleroangel.timetonic.infrastructure.exception

import kotlinx.serialization.SerializationException

/**
 * Failed to parse JSON, unexpected schema
 */
class UnexpectedResponse(raw: String, e: SerializationException? = null) :
    FailedRequest("Unexpected schema: $raw. Exception was: $e")