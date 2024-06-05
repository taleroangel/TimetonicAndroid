package dev.taleroangel.timetonic.domain.entities

/**
 * Wrapper around [String] to uniquely identify an application key
 */
@JvmInline
value class ApplicationKey(val value: String)