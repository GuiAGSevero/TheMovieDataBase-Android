package com.severo.marvel.framework

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.data.repository.CharactersRemoteDataSource
import com.severo.marvel.framework.db.AppDatabase
import com.severo.testing.model.CharacterFactory
import com.severo.testing.model.ComicFactory
import com.severo.testing.model.EventFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: CharactersRemoteDataSource

    @Mock
    private lateinit var appDatabase: AppDatabase

    private lateinit var repository: CharactersRepositoryImpl

    private val characterFactory = CharacterFactory()
    private val comicFactory = ComicFactory()
    private val eventFactory = EventFactory()

    private val character = characterFactory.create(CharacterFactory.Hero.ThreeDMan)

    @Before
    fun setUp() {
        repository = CharactersRepositoryImpl(remoteDataSource, appDatabase)
    }

    @Test
    fun `should return comics from remoteDataSource`() = runTest {
        val expected = listOf(comicFactory.create(ComicFactory.FakeComic.FakeComic1))
        whenever(remoteDataSource.fetchComics(character.id)).thenReturn(expected)

        val result = repository.getComics(character.id)

        assertEquals(expected, result)
        verify(remoteDataSource).fetchComics(character.id)
    }

    @Test
    fun `should return events from remoteDataSource`() = runTest {
        val expected = listOf(eventFactory.create(EventFactory.FakeEvent.FakeEvent1))
        whenever(remoteDataSource.fetchEvents(character.id)).thenReturn(expected)

        val result = repository.getEvents(character.id)

        assertEquals(expected, result)
        verify(remoteDataSource).fetchEvents(character.id)
    }
}
