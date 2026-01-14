package com.severo.marvel.framework.di

import com.severo.core.data.repository.StorageLocalDataSource
import com.severo.core.data.repository.StorageRepository
import com.severo.marvel.framework.StorageRepositoryImpl
import com.severo.marvel.framework.local.DataStorePreferencesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {

    single<StorageRepository> {
        StorageRepositoryImpl(get())
    }

    single<StorageLocalDataSource> {
        DataStorePreferencesDataSource(androidContext())
    }
}