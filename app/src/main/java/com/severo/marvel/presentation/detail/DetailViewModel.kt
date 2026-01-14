package com.severo.marvel.presentation.detail

import androidx.lifecycle.ViewModel
import com.severo.core.usecase.AddFavoriteUseCase
import com.severo.core.usecase.CheckFavoriteUseCase
import com.severo.core.usecase.GetCharacterCategoriesUseCase
import com.severo.core.usecase.RemoveFavoriteUseCase
import com.severo.core.usecase.base.CoroutinesDispatchers

class DetailViewModel(
    getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    removeFavoriteUseCase: RemoveFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase
    )

    val favorite = FavoriteUiActionStateLiveData(
        coroutinesDispatchers.main(),
        checkFavoriteUseCase,
        addFavoriteUseCase,
        removeFavoriteUseCase
    )
}