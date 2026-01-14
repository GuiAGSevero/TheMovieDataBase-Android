package com.severo.marvel.framework.di

import com.severo.core.data.repository.FavoritesLocalDataSource
import com.severo.core.data.repository.FavoritesRepository
import com.severo.marvel.framework.FavoritesRepositoryImpl
import com.severo.marvel.framework.local.RoomFavoritesDataSource
import org.koin.dsl.module

val favoritesModule = module {

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }

    single<FavoritesLocalDataSource> {
        RoomFavoritesDataSource(get())
    }
}
