package com.example.unittests.characters.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ConsumeCharactersUseCase {

    operator fun invoke(): Flow<List<Character>>

    class Base @Inject constructor(
        private val characterRepository: CharacterRepository,
    ) : ConsumeCharactersUseCase {

        override fun invoke(): Flow<List<Character>> {
            return characterRepository.consumeProducts()
        }

    }

}