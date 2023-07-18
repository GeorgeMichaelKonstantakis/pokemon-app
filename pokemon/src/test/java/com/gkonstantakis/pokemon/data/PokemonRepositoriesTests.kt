package com.gkonstantakis.pokemon.data

import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.repositories.impl.PokemonRepositoryImpl
import org.junit.Before

class PokemonRepositoriesTests {

    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setup() {
        pokemonRepository = PokemonRepositoryImpl()
        productRepository.setNewProducts(TestProducts().createTestProducts())
    }
}