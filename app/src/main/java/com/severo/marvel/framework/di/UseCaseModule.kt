package com.severo.marvel.framework.di

import com.severo.core.usecase.AddFavoriteUseCase
import com.severo.core.usecase.AddFavoriteUseCaseImpl
import com.severo.core.usecase.CheckFavoriteUseCase
import com.severo.core.usecase.CheckFavoriteUseCaseImpl
import com.severo.core.usecase.GetCharacterCategoriesUseCase
import com.severo.core.usecase.GetCharacterCategoriesUseCaseImpl
import com.severo.core.usecase.GetCharactersSortingUseCase
import com.severo.core.usecase.GetCharactersSortingUseCaseImpl
import com.severo.core.usecase.GetCharactersUseCase
import com.severo.core.usecase.GetCharactersUseCaseImpl
import com.severo.core.usecase.GetFavoritesUseCase
import com.severo.core.usecase.GetFavoritesUseCaseImpl
import com.severo.core.usecase.RemoveFavoriteUseCase
import com.severo.core.usecase.RemoveFavoriteUseCaseImpl
import com.severo.core.usecase.SaveCharactersSortingUseCase
import com.severo.core.usecase.SaveCharactersSortingUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    single<GetCharactersUseCase> {
        GetCharactersUseCaseImpl(
            charactersRepository = get(),
            storageRepository = get()
        )
    }

    single<GetCharactersSortingUseCase> {
        GetCharactersSortingUseCaseImpl(
            storageRepository = get(),
            sortingMapper = get(),
            dispatchers = get()
        )
    }

    single<SaveCharactersSortingUseCase> {
        SaveCharactersSortingUseCaseImpl(
            storageRepository = get(),
            sortingMapper = get(),
            dispatchers = get()
        )
    }

    single<GetFavoritesUseCase> {
        GetFavoritesUseCaseImpl(
            favoritesRepository = get(),
            dispatchers = get()
        )
    }

    single<GetCharacterCategoriesUseCase> {
        GetCharacterCategoriesUseCaseImpl(
            charactersRepository = get(),
            dispatchers = get()
        )
    }

    single<CheckFavoriteUseCase> {
        CheckFavoriteUseCaseImpl(
            favoritesRepository = get(),
            dispatchers = get()
        )
    }

    single<AddFavoriteUseCase> {
        AddFavoriteUseCaseImpl(
            favoritesRepository = get(),
            dispatchers = get()
        )
    }

    single<RemoveFavoriteUseCase> {
        RemoveFavoriteUseCaseImpl(
            favoritesRepository = get(),
            dispatchers = get()
        )
    }
}