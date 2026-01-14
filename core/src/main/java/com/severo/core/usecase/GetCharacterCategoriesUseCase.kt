package com.severo.core.usecase

import com.severo.core.data.repository.CharactersRepository
import com.severo.core.domain.model.Comic
import com.severo.core.domain.model.Event
import com.severo.core.usecase.base.CoroutinesDispatchers
import com.severo.core.usecase.base.ResultStatus
import com.severo.core.usecase.base.UseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GetCharacterCategoriesUseCase {

    operator fun invoke(params: GetCategoriesParams): Flow<ResultStatus<Pair<List<Comic>, List<Event>>>>

    data class GetCategoriesParams(val characterId: Int)
}

class GetCharacterCategoriesUseCaseImpl(
    private val charactersRepository: CharactersRepository,
    private val dispatchers: CoroutinesDispatchers
) : GetCharacterCategoriesUseCase,
    UseCase<GetCharacterCategoriesUseCase.GetCategoriesParams, Pair<List<Comic>, List<Event>>>() {

    override suspend fun doWork(
        params: GetCharacterCategoriesUseCase.GetCategoriesParams
    ): ResultStatus<Pair<List<Comic>, List<Event>>> {
        return withContext(dispatchers.io()) {
            val comicsDeferred = async { charactersRepository.getComics(params.characterId) }
            val eventsDeferred = async { charactersRepository.getEvents(params.characterId) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

            ResultStatus.Success(comics to events)
        }
    }
}