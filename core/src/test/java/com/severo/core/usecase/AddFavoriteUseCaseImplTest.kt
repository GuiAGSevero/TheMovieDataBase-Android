package com.severo.core.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.severo.core.data.repository.FavoritesRepository
import com.severo.core.domain.model.Character
import com.severo.core.usecase.AddFavoriteUseCase.Params
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
class AddFavoriteUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var favoritesRepository: FavoritesRepository

    private lateinit var useCase: AddFavoriteUseCase

    private val dispatcher = mainCoroutineRule.testDispatcherProvider

    private val params = Params(
        characterId = 101,
        name = "Iron Man",
        imageUrl = "https://image.com/ironman.png"
    )

    @Before
    fun setUp() {
        useCase = AddFavoriteUseCaseImpl(
            favoritesRepository,
            dispatcher
        )
    }

    @Test
    fun `should emit Loading and Success when favorite is added successfully`() = runTest {
        val result = useCase.invoke(params)

        val resultList = result.toList()

        verify(favoritesRepository).saveFavorite(
            Character(
                id = 101,
                name = "Iron Man",
                imageUrl = "https://image.com/ironman.png"
            )
        )

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Success)
        assertEquals(Unit, (resultList[1] as ResultStatus.Success).data)
    }

    @Test
    fun `should emit Loading and Error when repository throws exception`() = runTest {
        whenever(
            favoritesRepository.saveFavorite(
                Character(
                    id = 101,
                    name = "Iron Man",
                    imageUrl = "https://image.com/ironman.png"
                )
            )
        ).thenAnswer { throw RuntimeException("Database error") }

        val result = useCase.invoke(params)

        val resultList = result.toList()

        assertEquals(ResultStatus.Loading, resultList[0])
        assertTrue(resultList[1] is ResultStatus.Error)

        val error = resultList[1] as ResultStatus.Error
        assertTrue(error.throwable is RuntimeException)
        assertEquals("Database error", error.throwable.message)
    }
}
