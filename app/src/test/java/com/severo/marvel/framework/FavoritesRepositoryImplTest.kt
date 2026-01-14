package com.severo.marvel.framework

import com.nhaarman.mockitokotlin2.*
import com.severo.core.data.repository.FavoritesLocalDataSource
import com.severo.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesRepositoryImplTest {

    private lateinit var repository: FavoritesRepositoryImpl

    private val localDataSource: FavoritesLocalDataSource = mock()
    private val characterFactory = CharacterFactory()
    private val character = characterFactory.create(CharacterFactory.Hero.ThreeDMan)

    @Before
    fun setUp() {
        repository = FavoritesRepositoryImpl(localDataSource)
    }

    @Test
    fun `should return list of characters from localDataSource`() = runTest {
        val expected = listOf(character)
        whenever(localDataSource.getAll()).thenReturn(flowOf(expected))

        val result = repository.getAll().first()

        assertEquals(expected, result)
        verify(localDataSource).getAll()
    }

    @Test
    fun `should return true when character is favorite`() = runTest {
        whenever(localDataSource.isFavorite(character.id)).thenReturn(true)

        val result = repository.isFavorite(character.id)

        assertTrue(result)
        verify(localDataSource).isFavorite(character.id)
    }

    @Test
    fun `should call save on localDataSource`() = runTest {
        repository.saveFavorite(character)

        verify(localDataSource).save(character)
    }

    @Test
    fun `should call delete on localDataSource`() = runTest {
        repository.deleteFavorite(character)

        verify(localDataSource).delete(character)
    }
}
