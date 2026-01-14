package com.severo.marvel.presentation.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.domain.model.Character
import com.severo.core.usecase.GetFavoritesUseCase
import com.severo.marvel.extension.getOrAwaitValueTest
import com.severo.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private lateinit var favoritesViewModel: FavoritesViewModel

    private val mockCharacterList = listOf(
        Character(
            id = 1,
            name = "Iron Man",
            imageUrl = "http://image.com/ironman.jpg"
        ),
        Character(
            id = 2,
            name = "Hulk",
            imageUrl = "http://image.com/hulk.jpg"
        )
    )

    @Before
    fun setUp() {
        favoritesViewModel = FavoritesViewModel(
            getFavoritesUseCase,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should emit ShowFavorite when getAll returns a non-empty list`() = runTest {
        whenever(getFavoritesUseCase.invoke())
            .thenReturn(flowOf(mockCharacterList))

        favoritesViewModel.getAll()

        val state = favoritesViewModel.state.getOrAwaitValueTest()

        assertTrue(state is FavoritesViewModel.UiState.ShowFavorite)

        val favorites = (state as FavoritesViewModel.UiState.ShowFavorite).favorites

        assertEquals(2, favorites.size)
        assertEquals(1, favorites[0].id)
        assertEquals("Iron Man", favorites[0].name)
    }

    @Test
    fun `should emit ShowEmpty when getAll returns an empty list`() = runTest {
        whenever(getFavoritesUseCase.invoke())
            .thenReturn(flowOf(emptyList()))

        favoritesViewModel.getAll()

        val state = favoritesViewModel.state.getOrAwaitValueTest()

        assertTrue(state is FavoritesViewModel.UiState.ShowEmpty)
    }

    @Test
    fun `should emit ShowEmpty when getAll throws exception`() = runTest {
        whenever(getFavoritesUseCase.invoke())
            .thenReturn(flow { throw RuntimeException() })

        favoritesViewModel.getAll()

        val state = favoritesViewModel.state.getOrAwaitValueTest()

        assertTrue(state is FavoritesViewModel.UiState.ShowEmpty)
    }
}
