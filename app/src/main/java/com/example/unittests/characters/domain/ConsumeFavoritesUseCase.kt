package com.example.unittests.characters.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ConsumeFavoritesUseCase {

    operator fun invoke(): Flow<List<FavoriteCharacter>>

    class Base @Inject constructor(
        private val favoritesRepository: FavoritesRepository,
    ) : ConsumeFavoritesUseCase {

        override fun invoke(): Flow<List<FavoriteCharacter>> {
            return favoritesRepository.consumeFavorites()
        }
    }

}