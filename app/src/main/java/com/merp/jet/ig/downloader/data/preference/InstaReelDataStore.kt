package com.merp.jet.ig.downloader.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.merp.jet.ig.downloader.utils.Constants
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class InstaReelDataStore @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.INSTA_PREFERENCE)

    suspend fun putString(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getString(key: String): String? {
        val preference = context.dataStore.data.first()
        return preference[stringPreferencesKey(key)]
    }

    suspend fun getBoolean(key: String): Boolean? {
        val preference = context.dataStore.data.first()
        return preference[booleanPreferencesKey(key)]
    }
}