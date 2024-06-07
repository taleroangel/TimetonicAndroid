package dev.taleroangel.timetonic.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Manage application authentication state
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    /**
     * Service for requesting authentication
     */
    private val authService: IAuthService,

    /**
     * Keep state across configuration changes
     */
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        val TAG: String = AuthViewModel::class.simpleName!!
    }

    /**
     * Represents the current authentication state
     */
    private val _authState: MutableStateFlow<AuthViewState> =
        MutableStateFlow(
            savedStateHandle.get<AuthViewState>("auth:authState") ?: AuthViewState.NONE
        )

    /**
     * Set the UI state considering the [savedStateHandle]
     */
    private fun setState(state: AuthViewState) {
        _authState.value = state
        savedStateHandle["auth:authState"] = authState.value
    }

    /**
     * Get the authentication UI status (immutable)
     */
    val authState: StateFlow<AuthViewState> = _authState.asStateFlow()

    /**
     * Authentication API key
     */
    private val authKey: MutableLiveData<String> =
        savedStateHandle.getLiveData("auth:authKey", "")

    /**
     * Authentication credentials
     */
    private val authCredentials: MutableLiveData<UserCredentials?> =
        savedStateHandle.getLiveData("auth:authCredentials", null)

    /**
     * Authenticated user details
     */
    private val userDetails: MutableLiveData<UserDetails?> =
        savedStateHandle.getLiveData("auth:userDetails", null)

    /**
     * Authenticate an user and gather its credentials
     */
    fun authenticate(email: String, password: String) {

        setState(AuthViewState.AUTHENTICATING)

        viewModelScope.launch {
            // Check if auth key is present
            if (authKey.value!!.isEmpty()) {
                authService.key().fold(
                    { appKey ->
                        // Store the key
                        authKey.value = appKey.value
                    },
                    { err ->
                        // Emit the error
                        Log.e(TAG, "Failed to obtain API key", err)
                        setState(AuthViewState.FAILED)
                        return@launch
                    })
            }

            // Try to authenticate
            authService.authenticate(email, password, ApplicationKey(authKey.value!!)).fold(
                { credentials ->
                    // Store the credentials
                    authCredentials.value = credentials
                },
                { err ->
                    // Emit the error
                    Log.e(TAG, "Failed to authenticate", err)
                    setState(AuthViewState.FAILED)
                    return@launch
                })

            // Get user information
            authService.user(authCredentials.value!!).fold(
                { user ->
                    // Store the user details
                    userDetails.value = user
                }, { err ->
                    // Emit the error
                    Log.e(TAG, "Failed fetch user information", err)
                    setState(AuthViewState.FAILED)
                    return@launch
                })

            // Change to success
            setState(AuthViewState.AUTHENTICATED)
        }
    }

    /**
     * Restart the state, forget credentials or errors
     */
    fun restart() {
        // Set the new state
        setState(AuthViewState.NONE)

        // Reset variables
        authKey.value = ""
        authCredentials.value = null
        userDetails.value = null
    }
}