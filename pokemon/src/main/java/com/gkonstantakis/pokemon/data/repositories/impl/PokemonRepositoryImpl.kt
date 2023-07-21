package com.gkonstantakis.pokemon.data.repositories.impl

import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Paging
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonAbility
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonInfo
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonWithNoInfoAndPaging
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository

class PokemonRepositoryImpl(
    private val pokemonNetworkService: PokemonNetworkService,
    private val pokemonDao: PokemonDao,
    private val databaseMapper: DatabaseMapper,
    private val networkMapper: NetworkMapper
) : PokemonRepository {

    override suspend fun getNetworkPokemon(paging: String): PokemonWithNoInfoAndPaging {
        val networkPokemon = pokemonNetworkService.getPokemons(paging)
        val pokemons = networkMapper.mapNetworkToDomainPokemonWithNoInfoAndPaging(networkPokemon)
        return pokemons
    }

    override suspend fun getNetworkPokemonInfo(url: String): PokemonInfo {
        val networkPokemonInfo = pokemonNetworkService.getPokemonInfo(url)
        val pokemonInfo = networkMapper.mapNetworkToDomainPokemonInfo(networkPokemonInfo)
        return pokemonInfo
    }

    override suspend fun getDatabasePokemonWithAbilitiesByName(name: String): List<PokemonWIthAbilities> {
        val databasePokemonsWithAbilities = pokemonDao.getPokemonWithAbilitiesByName(name)
        val pokemonWithAbilities =
            databaseMapper.mapDatabaseListToDomainPokemonWithAbilitiesList(
                databasePokemonsWithAbilities
            )
        return pokemonWithAbilities
    }

    override suspend fun getDatabasePokemons(): List<Pokemon> {
        val databasePokemons = pokemonDao.getAllPokemons()
        val pokemons =
            databaseMapper.mapDatabaseListToDomainPokemonList(databasePokemons)
        return pokemons
    }

    override suspend fun insertPokemonToDatabase(pokemon: Pokemon): Long {
        return pokemonDao.insertPokemon(databaseMapper.mapDomainToDatabase(pokemon))
    }

    override suspend fun insertAbilityToDatabase(ability: Ability): Long {
        return pokemonDao.insertAbility(databaseMapper.mapDomainToDatabase(ability))
    }

    override suspend fun insertPokemonAbilityCrossRefToDatabase(pokemonAbility: PokemonAbility): Long {
        return pokemonDao.insertPokemonAbilityCrossRef(
            databaseMapper.mapDomainToDatabase(
                pokemonAbility
            )
        )
    }

    override suspend fun insertOrUpdatePagingToDatabase(paging: Paging): Long {
        return pokemonDao.insertOrUpdatePaging(databaseMapper.mapDomainToDatabase(paging))
    }

    override suspend fun getDatabasePaging(): Paging {
        val databasePaging = pokemonDao.getPaging().first()
        val paging = databaseMapper.mapDatabaseToDomain(databasePaging)
        return paging
    }


}