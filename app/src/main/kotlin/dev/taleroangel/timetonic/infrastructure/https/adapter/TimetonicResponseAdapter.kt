package dev.taleroangel.timetonic.infrastructure.https.adapter

/**
 * A request with a status response
 * This is a mixin class
 */
interface TimetonicResponseAdapter {

    /**
     * Request type
     */
    val req: String

    /**
     * Status flag
     */
    val status: String

    /**
     * Error code for bad requests
     */
    val errorCode: String?

    /**
     * Error message for bad requests
     */
    val errorMsg: String?

    /**
     * Check is response was successful
     */
    fun isSuccess(): Boolean = status == "ok"

    /**
     * Check if response has a failure
     */
    fun isFailure(): Boolean = !isSuccess()
}