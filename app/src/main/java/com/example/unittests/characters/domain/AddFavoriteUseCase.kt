package com.example.unittests.characters.domain

import javax.inject.Inject

interface AddFavoriteUseCase {

    suspend operator fun invoke(favoriteCharacter: FavoriteCharacter)

    class Base @Inject constructor(
        private val favoritesRepository: FavoritesRepository,
    ) : AddFavoriteUseCase {
        override suspend fun invoke(favoriteCharacter: FavoriteCharacter) {
            favoritesRepository.addToFavorites(favorite = favoriteCharacter)
        }
    }

}


