package com.severo.marvel

import android.app.Application
import com.severo.marvel.framework.di.charactersRepositoryModule
import com.severo.marvel.framework.di.coroutinesModule
import com.severo.marvel.framework.di.databaseModule
import com.severo.marvel.framework.di.favoritesModule
import com.severo.marvel.framework.di.imageModule
import com.severo.marvel.framework.di.networkModule
import com.severo.marvel.framework.di.sortingMapperModule
import com.severo.marvel.framework.di.storageModule
import com.severo.marvel.framework.di.useCaseModule
import com.severo.marvel.framework.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarvelApp)
            modules(appModule)
        }
    }

    val appModule = listOf(
        networkModule,
        imageModule,
        databaseModule,
        viewModelModule,
        coroutinesModule,
        useCaseModule,
        storageModule,
        charactersRepositoryModule,
        favoritesModule,
        sortingMapperModule
    )
}