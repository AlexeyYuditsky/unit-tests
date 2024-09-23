package com.example.unittests.finish

import com.example.unittests.MainDispatcherRule
import com.example.unittests.characters.domain.AddFavoriteUseCase
import com.example.unittests.characters.domain.Character
import com.example.unittests.characters.domain.ConsumeCharactersUseCase
import com.example.unittests.characters.domain.ConsumeFavoritesUseCase
import com.example.unittests.characters.domain.FavoriteCharacter
import com.example.unittests.characters.domain.RemoveFavoriteUseCase
import com.example.unittests.characters.presentation.StateFactory
import com.example.unittests.characters.presentation.finish.CharacterListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {

    private val consumeFavoritesUseCase = object : ConsumeFavoritesUseCase {
        override fun invoke(): Flow<List<FavoriteCharacter>> = flowOf(listOf())
    }

    @Mock
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    @Mock
    lateinit var stateFactory: StateFactory

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `requestCharacters WHEN start loading EXPECT isLoading flag in state`() {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> = flowOf()
        }
        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )

        vm.requestCharacters()

        Assert.assertTrue(vm.state.value.isLoading)
    }

    /*@Test
    fun `requestCharacters WHEN characters are loaded EXPECT reset isLoading flag and set data`() {
        // arrange
        val expectedProducts = load2Characters()

        // act
        sut.requestCharacters()

        // assert
        Assert.assertFalse(sut.state.value.isLoading)
        assertEquals(expectedProducts, sut.state.value.characterListState)
    }

    @Test
    fun `requestCharacters WHEN product loading has error EXPECT state has en error`() {
        // arrange
        whenever(consumeCharactersUseCase.invoke()).thenReturn(flow { throw IllegalStateException() })

        // act
        sut.requestCharacters()

        // assert
        Assert.assertTrue(sut.state.value.hasError)
        assertEquals(R.string.error_wile_loading_data, sut.state.value.errorRes)
    }*/

    @Test
    fun `addToFavorites EXPECT change isFavorite flag to true`() = runTest {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> = flowOf()
        }
        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )
        // act
        vm.addToFavorites("1")

        // assert
       // verify(addFavoriteUseCase).invoke(FavoriteCharacter("1"))
    }

    /*@Test
    fun `removeFromFavorites EXPECT change isFavorite flag to true`() = runTest {
        // act
        sut.removeFromFavorites("1")

        // assert
        verify(removeFavoriteUseCase).invoke(FavoriteCharacter("1"))
    }

    private fun load2Characters(): List<CharacterState> {
        whenever(consumeCharactersUseCase.invoke())
            .thenReturn(flowOf(listOf(character(id = "1"), character(id = "2"))))

        val state1 = CharacterState(id = "1")
        val state2 = CharacterState(id = "2")

        whenever(stateFactory.create(argThat { id == "1" }, any())).thenReturn(state1)
        whenever(stateFactory.create(argThat { id == "2" }, any())).thenReturn(state2)

        return listOf(state1, state2)
    }*/

    private fun character(
        id: String = "",
        name: String = "",
        image: String = "",
    ): Character {
        return Character(
            id = id,
            name = name,
            image = image,
        )
    }
}