package dev.taleroangel.timetonic.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.taleroangel.timetonic.domain.service.IAuthService
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


}