package com.severo.core.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.data.repository.FavoritesRepository
import com.severo.core.domain.model.Character
import com.severo.testing.MainCoroutineRule
import com.severo.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetFavoritesUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var favoritesRepository: FavoritesRepository

    private lateinit var useCase: GetFavoritesUseCase

    private val characterFactory = CharacterFactory()
    private val character = characterFactory.create(CharacterFactory.Hero.ThreeDMan)
    private val favoritesList = listOf(character)

    @Before
    fun setUp() {
        useCase = GetFavoritesUseCaseImpl(
            favoritesRepository,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return favorites list successfully`() = runTest {
        whenever(favoritesRepository.getAll()).thenReturn(flowOf(favoritesList))

        val result = useCase.invoke().first()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(character, result[0])

        verify(favoritesRepository).getAll()
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        whenever(favoritesRepository.getAll()).thenReturn(
            flow { throw RuntimeException("Database error") }
        )

        try {
            useCase.invoke().first()

            fail("Expected an exception but none was thrown")
        } catch (e: RuntimeException) {
            assertEquals("Database error", e.message)
        }

        verify(favoritesRepository).getAll()
    }
}
