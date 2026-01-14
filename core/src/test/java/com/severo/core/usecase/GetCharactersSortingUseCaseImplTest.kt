package com.severo.core.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.data.StorageConstants
import com.severo.core.data.mapper.SortingMapper
import com.severo.core.data.repository.StorageRepository
import com.severo.core.domain.model.SortingType
import com.severo.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersSortingUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var storageRepository: StorageRepository

    val sortingMapper: SortingMapper = SortingMapper()

    private lateinit var useCase: GetCharactersSortingUseCase

    private val dispatcher = mainCoroutineRule.testDispatcherProvider

    @Before
    fun setUp() {
        useCase = GetCharactersSortingUseCaseImpl(
            storageRepository,
            sortingMapper,
            dispatcher
        )
    }

    @Test
    fun `should return sorting pair correctly`() = runTest {
        whenever(storageRepository.sorting)
            .thenReturn(flowOf(StorageConstants.ORDER_BY_NAME_ASCENDING))

        val result = useCase.invoke().first()

        assertEquals("name" to "ascending", result)
    }

    @Test
    fun `should throw exception when repository emits error`() = runTest {
        val expectedException = RuntimeException("Database error")
        whenever(storageRepository.sorting).thenReturn(flow { throw expectedException })

        try {
            useCase.invoke().first()
            Assert.fail("Expected an exception but none was thrown")
        } catch (e: Exception) {
            assertEquals("Database error", e.message)
            verify(storageRepository).sorting
        }
    }
}
