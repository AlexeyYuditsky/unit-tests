package com.example.unittests.characters.domain

import javax.inject.Inject

interface RemoveFavoriteUseCase {

    suspend operator fun invoke(favoriteCharacter: FavoriteCharacter)

    class Base @Inject constructor(
        private val favoritesRepository: FavoritesRepository,
    ) : RemoveFavoriteUseCase {
        override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
            favoritesRepository.removeFromFavorites(favoriteCharacter)
        }
    }
}

