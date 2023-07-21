package com.gkonstantakis.pokemon.data.repositories

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Paging
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonAbility
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonInfo
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonWithNoInfoAndPaging
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import com.gkonstantakis.pokemon.data.testData.TestPokemonData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestPokemonRepositoryImpl : PokemonRepository {
    override suspend fun getNetworkPokemon(paging: String): PokemonWithNoInfoAndPaging {
        return TestPokemonData.getPokemonWithNoInfoAndPagingList()
    }

    override suspend fun getNetworkPokemonInfo(url: String): PokemonInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getDatabasePokemonWithAbilitiesByName(name: String): List<PokemonWIthAbilities> {
        val list = ArrayList<PokemonWIthAbilities>()
        list.add(TestPokemonData.getPokemonWithAbilities())
        return list
    }

    override suspend fun getDatabasePokemons(): List<Pokemon> {
        return TestPokemonData.getPokemonData()
    }

    override suspend fun insertPokemonToDatabase(pokemon: Pokemon): Long {
        return 0
    }

    override suspend fun insertAbilityToDatabase(ability: Ability): Long {
        return 0
    }

    override suspend fun insertPokemonAbilityCrossRefToDatabase(pokemonAbility: PokemonAbility): Long {
        return 0
    }

    override suspend fun insertOrUpdatePagingToDatabase(paging: Paging): Long {
        return 0
    }

    override suspend fun getDatabasePaging(): Paging {
        return Paging(
            pagingUrl = "paging"
        )
    }

}