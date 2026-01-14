package com.severo.marvel.framework

import com.nhaarman.mockitokotlin2.*
import com.severo.core.data.repository.StorageLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StorageRepositoryImplTest {

    private lateinit var repository: StorageRepositoryImpl

    private val storageLocalDataSource: StorageLocalDataSource = mock()

    private val sortingValue = "name|asc"

    @Before
    fun setUp() {
        repository = StorageRepositoryImpl(storageLocalDataSource)
    }

    @Test
    fun `should return sorting flow from localDataSource`() = runTest {
        whenever(storageLocalDataSource.sorting).thenReturn(flowOf(sortingValue))

        val result = repository.sorting.first()

        assertEquals(sortingValue, result)
        verify(storageLocalDataSource).sorting
    }

    @Test
    fun `should call saveSorting on localDataSource`() = runTest {
        repository.saveSorting(sortingValue)

        verify(storageLocalDataSource).saveSorting(sortingValue)
    }
}
