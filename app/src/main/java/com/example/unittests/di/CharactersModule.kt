package com.example.unittests.di

import com.example.unittests.characters.domain.AddFavoriteUseCase
import com.example.unittests.characters.domain.ConsumeCharactersUseCase
import com.example.unittests.characters.domain.ConsumeFavoritesUseCase
import com.example.unittests.characters.domain.RemoveFavoriteUseCase
import com.example.unittests.characters.presentation.StateFactory
import com.example.unittests.characters.presentation.StateFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface CharactersModule {
    @Binds
    fun bindStateFactory(
        stateFactoryImpl: StateFactoryImpl
    ): StateFactory

    @Binds
    fun bindConsumeFavoritesUseCase(useCase: ConsumeFavoritesUseCase.Base): ConsumeFavoritesUseCase

    @Binds
    fun bindConsumeCharactersUseCase(useCase: ConsumeCharactersUseCase.Base): ConsumeCharactersUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCase.Base): AddFavoriteUseCase

    @Binds
    fun bindRemoveFavoriteUseCase(useCase: RemoveFavoriteUseCase.Base): RemoveFavoriteUseCase

}
