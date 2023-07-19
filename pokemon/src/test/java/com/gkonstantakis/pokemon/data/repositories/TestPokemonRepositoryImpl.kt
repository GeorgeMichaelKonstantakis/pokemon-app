package com.gkonstantakis.pokemon.data.repositories

import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import com.gkonstantakis.pokemon.data.testData.TestPokemonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestPokemonRepositoryImpl : PokemonRepository {
    override suspend fun getNetworkPokemon(): Flow<PokemonState<List<Pokemon>>> =
        flow {
            emit(PokemonState.SuccessPokemon(TestPokemonData.getPokemonData()))
        }

    override suspend fun getNetworkPagingPokemon(): Flow<PokemonState<List<Pokemon>>> =
        flow {
            emit(PokemonState.SuccessPagingPokemon(TestPokemonData.getPokemonData()))
        }

    override suspend fun getDatabasePokemonWithAbilities(name: String): Flow<PokemonInfoState<List<PokemonWIthAbilities>>> =
        flow {
            emit(PokemonInfoState.Success(TestPokemonData.getPokemonsWithAbilities()))
        }
}