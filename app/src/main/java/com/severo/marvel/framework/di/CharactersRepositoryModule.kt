package com.severo.marvel.framework.di

import com.severo.core.data.repository.CharactersRepository
import com.severo.core.data.repository.CharactersRemoteDataSource
import com.severo.marvel.framework.CharactersRepositoryImpl
import com.severo.marvel.framework.remote.RetrofitCharactersDataSource
import org.koin.dsl.module

val charactersRepositoryModule = module {

    single<CharactersRepository> { CharactersRepositoryImpl(get(), get()) }

    single<CharactersRemoteDataSource> { RetrofitCharactersDataSource(get()) }
}