package com.likelion.agijagiseller.feature.login.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun updateUid(uid: String?) {
        dataStore.edit { preferences ->
            if (uid == null) {
                preferences.remove(UID)
            } else {
                preferences[UID] = uid
            }
        }
    }

    suspend fun updateEmail(email: String?) {
        dataStore.edit { preferences ->
            if (email == null) {
                preferences.remove(EMAIL)
            } else {
                preferences[EMAIL] = email
            }
        }
    }

    suspend fun updateLoginType(loginType: String?) {
        dataStore.edit { preferences ->
            if (loginType == null) {
                preferences.remove(LOGIN_TYPE)
            } else {
                preferences[LOGIN_TYPE] = loginType
            }
        }
    }

    suspend fun updateName(name: String?) {
        dataStore.edit { preferences ->
            if (name == null) {
                preferences.remove(NAME)
            } else {
                preferences[NAME] = name
            }
        }
    }

    val uid: Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[UID]
        }

    val email: Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[EMAIL]
        }

    val loginType: Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[LOGIN_TYPE]
        }

    val name: Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[NAME]
        }

    companion object {
        val UID = stringPreferencesKey("uid")
        val EMAIL = stringPreferencesKey("email")
        val LOGIN_TYPE = stringPreferencesKey("loginType")
        val NAME = stringPreferencesKey("name")
    }
}
