package com.severo.marvel.framework.di

import androidx.room.Room
import com.severo.marvel.framework.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        )
            .build()
    }

    single { get<AppDatabase>().favoriteDao() }
    single { get<AppDatabase>().characterDao() }
    single { get<AppDatabase>().remoteKeyDao() }
}