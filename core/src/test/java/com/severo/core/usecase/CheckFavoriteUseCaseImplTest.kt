package com.severo.core.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.verify
import com.severo.core.data.repository.FavoritesRepository
import com.severo.core.usecase.CheckFavoriteUseCase.Params
import com.severo.core.usecase.base.ResultStatus
import com.severo.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CheckFavoriteUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var favoritesRepository: FavoritesRepository

    private lateinit var useCase: CheckFavoriteUseCase

    private val dispatcher = mainCoroutineRule.testDispatcherProvider

    private val params = Params(characterId = 101)

    @Before
    fun setUp() {
        useCase = CheckFavoriteUseCaseImpl(
            favoritesRepository,
            dispatcher
        )
    }

    @Test
    fun `should emit Loading and Success true when character is favorite`() = runTest {
        whenever(favoritesRepository.isFavorite(101)).thenReturn(true)

        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(favoritesRepository).isFavorite(101)

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Success)
        assertEquals(true, (resultList[1] as ResultStatus.Success).data)
    }

    @Test
    fun `should emit Loading and Success false when character is not favorite`() = runTest {
        whenever(favoritesRepository.isFavorite(101)).thenReturn(false)

        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(favoritesRepository).isFavorite(101)

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Success)
        assertEquals(false, (resultList[1] as ResultStatus.Success).data)
    }

    @Test
    fun `should emit Loading and Error when repository throws exception`() = runTest {
        whenever(favoritesRepository.isFavorite(101))
            .thenAnswer { throw RuntimeException("Database error") }

        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(favoritesRepository).isFavorite(101)

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Error)

        val error = resultList[1] as ResultStatus.Error
        assertTrue(error.throwable is RuntimeException)
        assertEquals("Database error", error.throwable.message)
    }
}
