package com.example.gigmarket.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_ROLE_KEY = stringPreferencesKey("user_role")
    }

    suspend fun saveAuthData(token: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[JWT_TOKEN_KEY] = token
            prefs[USER_ROLE_KEY] = role
        }
    }

    fun getToken(): Flow<String?> = context.dataStore.data.map { it[JWT_TOKEN_KEY] }
    fun getRole(): Flow<String?> = context.dataStore.data.map { it[USER_ROLE_KEY] }

    suspend fun clearAuthData() {
        context.dataStore.edit { it.clear() }
    }
}
