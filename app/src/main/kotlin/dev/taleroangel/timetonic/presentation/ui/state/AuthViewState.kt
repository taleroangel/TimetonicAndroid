package dev.taleroangel.timetonic.presentation.ui.state

/**
 * Represents the state of the authentication UI
 */
enum class AuthViewState {
    /**
     * Initial state: Authentication is checking for stored credentials
     */
    INIT,

    /**
     * No authentication is provided, should show log in screen
     */
    NONE,

    /**
     * Trying authentication
     */
    AUTHENTICATING,

    /**
     * Authentication failed
     */
    FAILED,

    /**
     * Currently authenticated
     */
    AUTHENTICATED
}