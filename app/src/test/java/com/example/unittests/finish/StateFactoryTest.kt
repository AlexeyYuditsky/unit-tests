package com.example.unittests.finish

import com.example.unittests.characters.domain.Character
import com.example.unittests.characters.domain.FavoriteCharacter
import com.example.unittests.characters.presentation.StateFactory
import com.example.unittests.characters.presentation.StateFactoryImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StateFactoryTest {

    private val sut = StateFactoryImpl()

    @Test
    fun `create WHEN favorites are empty EXPECT isFavorite is false`() {
        // arrange
        val character = Character(id = "1", name = "Rick", image = "image")

        // act
        val state = sut.create(character, setOf())

        // assert
        Assert.assertFalse(state.isFavorite)
    }

    @Test
    fun `create WHEN my character is in favorites EXPECT isFavorite is true`() {
        // arrange
        val character = Character(id = "1", name = "Rick", image = "image")
        val favorites = setOf(FavoriteCharacter("1"))

        // act
        val productState = sut.create(character, favorites)

        // assert
        Assert.assertTrue(productState.isFavorite)
    }

    @Test
    fun `create WHEN my character is not in favorites EXPECT isFavorite is false`() {
        // arrange
        val character = Character(id = "1", name = "Rick", image = "image")
        val favorites = setOf(FavoriteCharacter("2"))

        // act
        val productState = sut.create(character, favorites)

        // assert
        Assert.assertFalse(productState.isFavorite)
    }
}
