package com.severo.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nhaarman.mockitokotlin2.*
import com.severo.core.data.repository.CharactersRepository
import com.severo.core.data.repository.StorageRepository
import com.severo.core.domain.model.Character
import com.severo.testing.MainCoroutineRule
import com.severo.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var charactersRepository: CharactersRepository

    @Mock
    lateinit var storageRepository: StorageRepository

    private lateinit var useCase: GetCharactersUseCase

    private val pagingConfig = PagingConfig(pageSize = 20)

    private val characterFactory = CharacterFactory()
    private val character = characterFactory.create(CharacterFactory.Hero.ThreeDMan)

    private val sorting = "name|ASC"

    @Before
    fun setUp() {
        useCase = GetCharactersUseCaseImpl(
            charactersRepository,
            storageRepository
        )
    }

    @Test
    fun `should return paging data successfully`() = runTest {
        whenever(storageRepository.sorting).thenReturn(flowOf(sorting))
        whenever(
            charactersRepository.getCachedCharacters(
                query = "",
                orderBy = sorting,
                pagingConfig = pagingConfig
            )
        ).thenReturn(
            flowOf(PagingData.from(listOf(character)))
        )

        val resultFlow = useCase.invoke(
            GetCharactersUseCase.GetCharactersParams(
                query = "",
                pagingConfig = pagingConfig
            )
        )

        val emitted = resultFlow.first()

        assertNotNull(emitted)

        verify(storageRepository).sorting
        verify(charactersRepository).getCachedCharacters("", sorting, pagingConfig)
    }


    @Test
    fun `should throw exception when storageRepository fails`() = runTest {
        whenever(storageRepository.sorting).thenReturn(
            flow { throw RuntimeException("Storage error") }
        )

        try {
            val flowResult = useCase.invoke(
                GetCharactersUseCase.GetCharactersParams(
                    query = "",
                    pagingConfig = pagingConfig
                )
            )

            flowResult.collect {}

            fail("Expected an exception but none was thrown")
        } catch (e: RuntimeException) {
            assertEquals("Storage error", e.message)
        }

        verify(storageRepository).sorting
    }

    @Test
    fun `should throw exception when charactersRepository fails`() = runTest {
        whenever(storageRepository.sorting).thenReturn(flowOf(sorting))
        whenever(
            charactersRepository.getCachedCharacters(
                query = "",
                orderBy = sorting,
                pagingConfig = pagingConfig
            )
        ).thenReturn(
            flow { throw RuntimeException("Repository error") }
        )

        try {
            val flowResult = useCase.invoke(
                GetCharactersUseCase.GetCharactersParams(
                    query = "",
                    pagingConfig = pagingConfig
                )
            )

            flowResult.collect {}

            fail("Expected an exception but none was thrown")
        } catch (e: RuntimeException) {
            assertEquals("Repository error", e.message)
        }

        verify(storageRepository).sorting
        verify(charactersRepository).getCachedCharacters("", sorting, pagingConfig)
    }
}
