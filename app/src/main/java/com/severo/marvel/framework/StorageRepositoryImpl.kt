package com.severo.marvel.framework

import com.severo.core.data.repository.StorageLocalDataSource
import com.severo.core.data.repository.StorageRepository
import kotlinx.coroutines.flow.Flow

class StorageRepositoryImpl(
    private val storageLocalDataSource: StorageLocalDataSource,
) : StorageRepository {

    override val sorting: Flow<String>
        get() = storageLocalDataSource.sorting

    override suspend fun saveSorting(sorting: String) {
        storageLocalDataSource.saveSorting(sorting)
    }
}