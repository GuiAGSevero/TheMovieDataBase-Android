package com.severo.marvel.framework

import com.severo.core.data.repository.FavoritesLocalDataSource
import com.severo.core.data.repository.FavoritesRepository
import com.severo.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
) : FavoritesRepository {

    override fun getAll(): Flow<List<Character>> {
        return favoritesLocalDataSource.getAll()
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoritesLocalDataSource.isFavorite(characterId)
    }

    override suspend fun saveFavorite(character: Character) {
        favoritesLocalDataSource.save(character)
    }

    override suspend fun deleteFavorite(character: Character) {
        favoritesLocalDataSource.delete(character)
    }
}