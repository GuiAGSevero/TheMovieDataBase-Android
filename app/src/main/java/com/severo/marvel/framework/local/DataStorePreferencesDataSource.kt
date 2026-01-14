package com.severo.marvel.framework.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.severo.core.data.StorageConstants
import com.severo.core.data.repository.StorageLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferencesDataSource(
    private val context: Context
) : StorageLocalDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = StorageConstants.MARVEL_DATA_STORE_NAME
    )

    private val sortingKey = stringPreferencesKey(StorageConstants.SORT_ORDER_BY_KEY)

    override val sorting: Flow<String>
        get() = context.dataStore.data.map { marvelStore ->
            marvelStore[sortingKey] ?: ""
        }

    override suspend fun saveSorting(sorting: String) {
        context.dataStore.edit { marvelStore ->
            marvelStore[sortingKey] = sorting
        }
    }
}