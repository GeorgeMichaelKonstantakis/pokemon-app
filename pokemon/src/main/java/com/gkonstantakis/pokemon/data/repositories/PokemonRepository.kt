package com.gkonstantakis.pokemon.data.repositories

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Paging
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonAbility
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonInfo
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonWithNoInfoAndPaging
import retrofit2.http.Url

interface PokemonRepository {

    suspend fun getNetworkPokemon(paging: String): PokemonWithNoInfoAndPaging

    suspend fun getNetworkPokemonInfo(@Url url: String): PokemonInfo

    suspend fun getDatabasePokemonWithAbilitiesByName(name: String): List<PokemonWIthAbilities>

    suspend fun getDatabasePokemons(): List<Pokemon>

    suspend fun insertPokemonToDatabase(pokemon: Pokemon): Long

    suspend fun insertAbilityToDatabase(ability: Ability): Long

    suspend fun insertPokemonAbilityCrossRefToDatabase(pokemonAbility: PokemonAbility): Long

    suspend fun insertOrUpdatePagingToDatabase(paging: Paging): Long

    suspend fun getDatabasePaging(): Paging
}