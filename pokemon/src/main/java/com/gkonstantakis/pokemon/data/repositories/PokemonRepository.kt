package com.gkonstantakis.pokemon.data.repositories

import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getNetworkPokemon(): Flow<PokemonState<List<Pokemon>>>

    suspend fun getDatabasePokemonWithAbilities(name: String): Flow<PokemonInfoState<List<PokemonWIthAbilities>>>
}