package dev.taleroangel.timetonic.infrastructure.persistance

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.taleroangel.timetonic.domain.entities.ApplicationKey
import dev.taleroangel.timetonic.domain.entities.UserCredentials
import dev.taleroangel.timetonic.domain.repository.IAuthRepository
import dev.taleroangel.timetonic.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Create a [DataStore] to persist authentication
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AuthViewModel.TAG)

class AuthDataStore @Inject constructor(
    @ApplicationContext context: Context,
) : IAuthRepository {

    companion object {
        /**
         * TAG for using with [Log], [SavedStateHandle] and [DataStore]
         */
        val TAG: String = AuthViewModel::class.simpleName!!
    }

    private object PreferencesKey {
        val applicationKey = stringPreferencesKey("$TAG:applicationKey")
        val credentials = stringPreferencesKey("$TAG:credentials")
    }

    /**
     * [DataStore] for the application
     */
    private val dataStore = context.dataStore

    /**
     * Get the [ApplicationKey] stored as [String] if present
     */
    override val applicationKey: Flow<ApplicationKey?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.applicationKey]?.let {
                ApplicationKey(it)
            }
        }

    /**
     * Store the [ApplicationKey] as [String]
     */
    override suspend fun storeApplicationKey(applicationKey: ApplicationKey?) {
        dataStore.edit { preferences ->
            if (applicationKey == null) {
                preferences.remove(PreferencesKey.applicationKey)
            } else {
                preferences[PreferencesKey.applicationKey] = applicationKey.value
            }
        }
    }

    /**
     * Get the [UserCredentials] stored as [Json] if present
     */
    override val credentials: Flow<UserCredentials?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.credentials]?.let {
                Json.decodeFromString(it)
            }
        }

    /**
     * Store the [UserCredentials] as [Json]
     */
    override suspend fun storeCredentials(userCredentials: UserCredentials?) {
        dataStore.edit { preferences ->
            if (userCredentials == null) {
                preferences.remove(PreferencesKey.credentials)
            } else {
                preferences[PreferencesKey.credentials] = Json.encodeToString(userCredentials)
            }
        }
    }
}