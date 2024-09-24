package com.example.unittests.finish

import com.example.unittests.MainDispatcherRule
import com.example.unittests.characters.domain.AddFavoriteUseCase
import com.example.unittests.characters.domain.Character
import com.example.unittests.characters.domain.ConsumeCharactersUseCase
import com.example.unittests.characters.domain.ConsumeFavoritesUseCase
import com.example.unittests.characters.domain.FavoriteCharacter
import com.example.unittests.characters.domain.RemoveFavoriteUseCase
import com.example.unittests.characters.presentation.CharacterState
import com.example.unittests.characters.presentation.StateFactory
import com.example.unittests.characters.presentation.finish.CharacterListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import com.example.unittests.R

class CharacterListViewModelTest {

    private val consumeFavoritesUseCase = object : ConsumeFavoritesUseCase {
        override fun invoke(): Flow<List<FavoriteCharacter>> = flowOf(listOf())
    }

    private val stateFactory = object : StateFactory {
        override fun create(
            character: Character,
            favorites: Set<FavoriteCharacter>
        ): CharacterState {
            return if (character.id == "1") {
                CharacterState(id = "1")
            } else {
                CharacterState(id = "2")
            }
        }
    }

    private val removeFavoriteUseCase = object : RemoveFavoriteUseCase {
        lateinit var passedFavoriteCharacter: FavoriteCharacter

        override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
            passedFavoriteCharacter = favoriteCharacter
        }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `requestCharacters WHEN start loading EXPECT isLoading flag in state`() {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> = flowOf()
        }
        val addFavoriteUseCase = object : AddFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
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

    @Test
    fun `requestCharacters WHEN characters are loaded EXPECT reset isLoading flag and set data`() {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> {
                return flowOf(listOf(character(id = "1"), character(id = "2")))
            }
        }

        val addFavoriteUseCase = object : AddFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
        }

        val stateFactory = object : StateFactory {
            override fun create(
                character: Character,
                favorites: Set<FavoriteCharacter>
            ): CharacterState {
                return if (character.id == "1") {
                    CharacterState(id = "1")
                } else {
                    CharacterState(id = "2")
                }
            }
        }

        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )
        val expectedProducts = listOf(CharacterState(id = "1"), CharacterState(id = "2"))

        vm.requestCharacters()

        Assert.assertFalse(vm.state.value.isLoading)
        assertEquals(expectedProducts, vm.state.value.characterListState)
    }

    @Test
    fun `requestCharacters WHEN product loading has error EXPECT state has en error`() {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> {
                return flow { throw IllegalStateException() }
            }
        }

        val addFavoriteUseCase = object : AddFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
        }

        val stateFactory = object : StateFactory {
            override fun create(
                character: Character,
                favorites: Set<FavoriteCharacter>
            ): CharacterState {
                return if (character.id == "1") {
                    CharacterState(id = "1")
                } else {
                    CharacterState(id = "2")
                }
            }
        }

        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )

        vm.requestCharacters()

        Assert.assertTrue(vm.state.value.hasError)
        assertEquals(R.string.error_wile_loading_data, vm.state.value.errorRes)
    }

    @Test
    fun `addToFavorites EXPECT change isFavorite flag to true`() = runTest {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> = flowOf()
        }
        val addFavoriteUseCase = object : AddFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
        }

        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )

        vm.addToFavorites("1")

        val expected = FavoriteCharacter("1")
        val actual = addFavoriteUseCase.passedFavoriteCharacter

        assertEquals(expected, actual)
    }

    @Test
    fun `removeFromFavorites EXPECT change isFavorite flag to true`() = runTest {
        val consumeCharactersUseCase = object : ConsumeCharactersUseCase {
            override fun invoke(): Flow<List<Character>> = flowOf()
        }
        val removeFavoriteUseCase = object : RemoveFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
        }
        val addFavoriteUseCase = object : AddFavoriteUseCase {
            lateinit var passedFavoriteCharacter: FavoriteCharacter

            override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
                passedFavoriteCharacter = favoriteCharacter
            }
        }

        val vm = CharacterListViewModel(
            consumeCharactersUseCase = consumeCharactersUseCase,
            stateFactory = stateFactory,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
        )
        vm.removeFromFavorites("1")

        val expected = FavoriteCharacter("1")
        val actual = removeFavoriteUseCase.passedFavoriteCharacter

        assertEquals(expected, actual)
    }

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