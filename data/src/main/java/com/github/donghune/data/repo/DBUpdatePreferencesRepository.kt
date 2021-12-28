package com.github.donghune.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class DBUpdatePreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val latestUpdatedFlow: Flow<Long>
        get() = context.dataStore.data
            .map { value: Preferences ->
                value[LATEST_UPDATED] ?: 0
            }

    suspend fun updateLatestUpdated() {
        context.dataStore.edit { value ->
            value[LATEST_UPDATED] = Calendar.getInstance().timeInMillis
        }
    }

    val popularityUpdatedFlow: Flow<Long>
        get() = context.dataStore.data
            .map { value: Preferences ->
                value[POPULARITY_UPDATED] ?: 0
            }

    suspend fun updatePopularityUpdated() {
        context.dataStore.edit { value ->
            value[POPULARITY_UPDATED] = Calendar.getInstance().timeInMillis
        }
    }

    companion object {
        private val LATEST_UPDATED = longPreferencesKey("LATEST_UPDATED")
        private val POPULARITY_UPDATED = longPreferencesKey("POPULARITY_UPDATED")
    }

}