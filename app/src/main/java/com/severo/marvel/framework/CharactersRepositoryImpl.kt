package com.severo.marvel.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.severo.core.data.repository.CharactersRemoteDataSource
import com.severo.core.data.repository.CharactersRepository
import com.severo.core.domain.model.Character
import com.severo.core.domain.model.Comic
import com.severo.core.domain.model.Event
import com.severo.marvel.framework.db.AppDatabase
import com.severo.marvel.framework.paging.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val database: AppDatabase
) : CharactersRepository {

    override fun getCachedCharacters(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = CharactersRemoteMediator(query, orderBy, database, remoteDataSource)
        ) {
            database.characterDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                Character(
                    it.id,
                    it.name,
                    it.imageUrl
                )
            }
        }
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}