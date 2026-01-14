package com.severo.core.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.data.StorageConstants
import com.severo.core.data.mapper.SortingMapper
import com.severo.core.data.repository.StorageRepository
import com.severo.core.domain.model.SortingType
import com.severo.core.usecase.SaveCharactersSortingUseCase.Params
import com.severo.core.usecase.base.ResultStatus
import com.severo.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SaveCharactersSortingUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var storageRepository: StorageRepository

    private val sortingMapper = SortingMapper()

    private lateinit var useCase: SaveCharactersSortingUseCase

    private val dispatcher = mainCoroutineRule.testDispatcherProvider

    private val params = Params(
        sortingPair = SortingType.ORDER_BY_NAME.value to SortingType.ORDER_ASCENDING.value
    )

    @Before
    fun setUp() {
        useCase = SaveCharactersSortingUseCaseImpl(
            storageRepository,
            sortingMapper,
            dispatcher
        )
    }

    @Test
    fun `should emit Loading and Success when sorting is saved successfully`() = runTest {
        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(storageRepository).saveSorting(StorageConstants.ORDER_BY_NAME_ASCENDING)

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Success)
        assertEquals(Unit, (resultList[1] as ResultStatus.Success).data)
    }

    @Test
    fun `should emit Loading and Error when repository throws exception`() = runTest {
        whenever(storageRepository.saveSorting(StorageConstants.ORDER_BY_NAME_ASCENDING))
            .thenAnswer { throw RuntimeException("Database error") }

        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(storageRepository).saveSorting(StorageConstants.ORDER_BY_NAME_ASCENDING)

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Error)

        val error = resultList[1] as ResultStatus.Error
        assertTrue(error.throwable is RuntimeException)
        assertEquals("Database error", error.throwable.message)
    }
}
