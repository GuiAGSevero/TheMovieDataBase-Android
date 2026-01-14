package com.severo.marvel.presentation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.usecase.GetCharactersUseCase
import com.severo.testing.MainCoroutineRule
import com.severo.marvel.extension.getOrAwaitValueTest
import com.severo.testing.model.CharacterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    private val charactersFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABomb)
        )
    )

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(
            getCharactersUseCase,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should validate the paging data object when calling charactersPagingData`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenReturn(flowOf(pagingDataCharacters))

        val result = charactersViewModel.charactersPagingData("").first()

        assertNotNull(result)
    }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the use case returns error`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenThrow(RuntimeException())

        charactersViewModel.charactersPagingData("").first()
    }

    @Test
    fun `should emit UiState SearchResult when searchCharacters is called`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenReturn(flowOf(pagingDataCharacters))

        charactersViewModel.searchCharacters()

        val state = charactersViewModel.state.getOrAwaitValueTest()

        assertTrue(state is CharactersViewModel.UiState.SearchResult)
    }

    @Test
    fun `should emit UiState SearchResult when applySort is called`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenReturn(flowOf(pagingDataCharacters))

        charactersViewModel.applySort()

        val state = charactersViewModel.state.getOrAwaitValueTest()

        assertTrue(state is CharactersViewModel.UiState.SearchResult)
    }

    @Test
    fun `should clear currentSearchQuery when closeSearch is called`() {
        charactersViewModel.currentSearchQuery = "Iron Man"

        charactersViewModel.closeSearch()

        assertTrue(charactersViewModel.currentSearchQuery.isEmpty())
    }

    @Test
    fun `should keep currentSearchQuery empty if already empty when closeSearch is called`() {
        charactersViewModel.currentSearchQuery = ""

        charactersViewModel.closeSearch()

        assertTrue(charactersViewModel.currentSearchQuery.isEmpty())
    }

    @Test
    fun `should validate UiState SearchResult contains correct data`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenReturn(flowOf(pagingDataCharacters))

        charactersViewModel.searchCharacters()

        val state = charactersViewModel.state.getOrAwaitValueTest()

        assertTrue(state is CharactersViewModel.UiState.SearchResult)
        val result = (state as CharactersViewModel.UiState.SearchResult).data

        assertNotNull(result)
    }
}
