package dev.taleroangel.timetonic.infrastructure.exception

sealed class BadRequest(reason: String) : TimetonicException("BadException: $reason")