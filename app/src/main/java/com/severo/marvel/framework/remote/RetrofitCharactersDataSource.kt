package com.severo.marvel.framework.remote

import com.severo.core.data.repository.CharactersRemoteDataSource
import com.severo.core.domain.model.CharacterPaging
import com.severo.core.domain.model.Comic
import com.severo.core.domain.model.Event
import com.severo.marvel.framework.network.MarvelApi
import com.severo.marvel.framework.network.response.toCharacterModel
import com.severo.marvel.framework.network.response.toComicModel
import com.severo.marvel.framework.network.response.toEventModel

class RetrofitCharactersDataSource(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }

        return CharacterPaging(
            data.offset,
            data.total,
            characters
        )
    }

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId).data.results.map {
            it.toEventModel()
        }
    }
}