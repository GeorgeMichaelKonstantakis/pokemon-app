package com.gkonstantakis.pokemon.ui.mappers

import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.repositories.TestPokemonRepositoryImpl
import com.gkonstantakis.pokemon.data.repositories.impl.PokemonRepositoryImpl
import org.junit.Before

class UiMapperTests {

    private lateinit var pokemons: List<Po>

    @Before
    fun setup() {
        pokemonRepository = TestPokemonRepositoryImpl()
        productRepository.setNewProducts(TestProducts().createTestProducts())
    }
}