package dev.taleroangel.timetonic.presentation.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.entities.UserDetails
import dev.taleroangel.timetonic.domain.repository.IAuthRepository
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.presentation.ui.state.AuthViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
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
     * Stored credentials
     */
    private val authRepository: IAuthRepository,

    /**
     * Keep state across configuration changes
     */
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        /**
         * TAG for using with [Log], [SavedStateHandle] and [DataStore]
         */
        val TAG: String = AuthViewModel::class.simpleName!!
    }

    /**
     * Check if credentials are stored
     */
    init {
        viewModelScope.launch {
            // Get the application key
            val appKey = authRepository.applicationKey.first()
            if (appKey == null) {
                // Not authenticated
                _authState.value = AuthViewState.NONE
                return@launch
            }

            // Grab the value from the repository
            authKey.value = appKey.value

            // Get the stored credentials
            val credentials = authRepository.credentials.first()
            if (credentials == null) {
                // Not authenticated
                _authState.value = AuthViewState.NONE
                return@launch
            }

            // Grab the value from the repository
            _authCredentials.value = credentials


            // Authenticate user
            authService.user(_authCredentials.value!!).fold(
                { user ->
                    // Store the user details
                    _userDetails.value = user
                    // Change to success
                    _authState.value = AuthViewState.AUTHENTICATED
                }, { err ->
                    // Emit the error
                    Log.e(TAG, "Failed fetch user information", err)
                    // Restart authentication variables
                    restart()
                })
        }
    }

    /**
     * Represents the current authentication state
     */
    private val _authState: MutableLiveData<AuthViewState> =
        savedStateHandle.getLiveData("$TAG:authState", AuthViewState.INIT)

    /**
     * Get the authentication UI status (immutable)
     */
    val authState: LiveData<AuthViewState> = _authState

    /**
     * Authentication API key
     */
    private val authKey: MutableLiveData<String> =
        savedStateHandle.getLiveData("$TAG:authKey", "")

    /**
     * Authentication credentials
     */
    private val _authCredentials: MutableLiveData<UserCredentials?> =
        savedStateHandle.getLiveData("$TAG:authCredentials", null)

    /**
     * Authentication credentials (immutable)
     */
    val authCredentials: LiveData<UserCredentials?>
        get() = _authCredentials

    /**
     * Authenticated user details
     */
    private val _userDetails: MutableLiveData<UserDetails?> =
        savedStateHandle.getLiveData("$TAG:userDetails", null)

    /**
     * Get the user details (immutable)
     */
    val userDetails: LiveData<UserDetails?>
        get() = _userDetails

    /**
     * Should store credentials in [IAuthRepository]
     */
    val rememberCredentials: MutableStateFlow<Boolean> = MutableStateFlow(false)

    /**
     * Authenticate an user and gather its credentials
     */
    fun authenticate(email: String, password: String) {

        _authState.value = AuthViewState.AUTHENTICATING

        viewModelScope.launch {
            // Get the application key
            authService.key().fold(
                { appKey ->
                    // Store the key
                    authKey.value = appKey.value
                    // Store it in persistent storage
                    if (rememberCredentials.value) {
                        authRepository.storeApplicationKey(appKey)
                    }
                },
                { err ->
                    // Emit the error
                    Log.e(TAG, "Failed to obtain API key", err)
                    _authState.value = AuthViewState.FAILED
                    return@launch
                })

            // Try to authenticate
            authService.authenticate(email, password, ApplicationKey(authKey.value!!)).fold(
                { credentials ->
                    // Store the credentials
                    _authCredentials.value = credentials
                    // Store it in persistent storage
                    if (rememberCredentials.value) {
                        authRepository.storeCredentials(credentials)
                    }
                },
                { err ->
                    // Emit the error
                    Log.e(TAG, "Failed to authenticate", err)
                    _authState.value = AuthViewState.FAILED
                    return@launch
                })

            // Get user information
            authService.user(_authCredentials.value!!).fold(
                { user ->
                    // Store the user details
                    _userDetails.value = user
                }, { err ->
                    // Emit the error
                    Log.e(TAG, "Failed fetch user information", err)
                    _authState.value = AuthViewState.FAILED
                    return@launch
                })

            // Change to success
            _authState.value = AuthViewState.AUTHENTICATED
        }
    }

    /**
     * Restart the state, forget credentials or errors
     */
    fun restart() {
        // Set the new state
        _authState.value = AuthViewState.NONE

        // Reset variables
        authKey.value = ""
        _authCredentials.value = null
        _userDetails.value = null

        // Delete data from repository
        viewModelScope.launch {
            authRepository.storeApplicationKey(null)
            authRepository.storeCredentials(null)
        }
    }

    /**
     * Alias for [restart].
     * Just for code clarity
     */
    fun logout() = restart()
}