package com.severo.marvel.presentation.sort

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.usecase.GetCharactersSortingUseCase
import com.severo.core.usecase.SaveCharactersSortingUseCase
import com.severo.core.usecase.base.ResultStatus
import com.severo.marvel.extension.getOrAwaitValueTest
import com.severo.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SortViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCharactersSortingUseCase: GetCharactersSortingUseCase

    @Mock
    lateinit var saveCharactersSortingUseCase: SaveCharactersSortingUseCase

    private lateinit var sortViewModel: SortViewModel

    private val storedSorting = "name" to "ASC"

    @Before
    fun setUp() = runTest {
        whenever(getCharactersSortingUseCase.invoke())
            .thenReturn(flowOf(storedSorting))

        sortViewModel = SortViewModel(
            getCharactersSortingUseCase,
            saveCharactersSortingUseCase,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should emit SortingResult when ViewModel is initialized`() = runTest {
        val state = sortViewModel.state.getOrAwaitValueTest()

        assertTrue(state is SortViewModel.UiState.SortingResult)

        val sorting = (state as SortViewModel.UiState.SortingResult).storedSorting

        assertEquals("name", sorting.first)
        assertEquals("ASC", sorting.second)
    }

    @Test
    fun `should emit Loading and Success when applySorting is successful`() = runTest {
        whenever(
            saveCharactersSortingUseCase.invoke(
                SaveCharactersSortingUseCase.Params("name" to "DESC")
            )
        ).thenReturn(
            flow {
                emit(ResultStatus.Loading)
                emit(ResultStatus.Success(Unit))
            }
        )

        val emittedStates = mutableListOf<SortViewModel.UiState>()
        sortViewModel.state.observeForever { emittedStates.add(it) }

        emittedStates.clear()

        sortViewModel.applySorting("name", "DESC")

        advanceUntilIdle()

        assertTrue(emittedStates[0] is SortViewModel.UiState.ApplyState.Loading)
        assertTrue(emittedStates[1] is SortViewModel.UiState.ApplyState.Success)
    }

    @Test
    fun `should emit Loading and Error when applySorting fails`() = runTest {
        whenever(
            saveCharactersSortingUseCase.invoke(
                SaveCharactersSortingUseCase.Params("name" to "DESC")
            )
        ).thenReturn(
            flow {
                emit(ResultStatus.Loading)
                emit(ResultStatus.Error(Throwable("Save error")))
            }
        )

        val emittedStates = mutableListOf<SortViewModel.UiState>()
        sortViewModel.state.observeForever { emittedStates.add(it) }

        emittedStates.clear()

        sortViewModel.applySorting("name", "DESC")

        advanceUntilIdle()

        assertTrue(emittedStates[0] is SortViewModel.UiState.ApplyState.Loading)
        assertTrue(emittedStates[1] is SortViewModel.UiState.ApplyState.Error)
    }
}