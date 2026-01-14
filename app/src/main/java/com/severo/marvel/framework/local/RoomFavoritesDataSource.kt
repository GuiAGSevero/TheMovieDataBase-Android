package com.severo.marvel.framework.local

import com.severo.core.data.repository.FavoritesLocalDataSource
import com.severo.core.domain.model.Character
import com.severo.marvel.framework.db.dao.FavoriteDao
import com.severo.marvel.framework.db.entity.FavoriteEntity
import com.severo.marvel.framework.db.entity.toCharactersModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomFavoritesDataSource(
    private val favoriteDao: FavoriteDao
) : FavoritesLocalDataSource {

    override fun getAll(): Flow<List<Character>> {
        return favoriteDao.loadFavorites().map {
            it.toCharactersModel()
        }
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteDao.hasFavorite(characterId) != null
    }

    override suspend fun save(character: Character) {
        favoriteDao.insertFavorite(character.toFavoriteEntity())
    }

    override suspend fun delete(character: Character) {
        favoriteDao.deleteFavorite(character.toFavoriteEntity())
    }

    private fun Character.toFavoriteEntity() = FavoriteEntity(id, name, imageUrl)
}