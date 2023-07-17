package com.gkonstantakis.pokemon.data.repositories.impl

import android.util.Log
import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.ArrayList

class PokemonRepositoryImpl(
    private val pokemonNetworkService: PokemonNetworkService,
    private val pokemonDao: PokemonDao,
    private val databaseMapper: DatabaseMapper,
    private val networkMapper: NetworkMapper
) : PokemonRepository {

    override suspend fun getNetworkPokemon(): Flow<PokemonState<List<Pokemon>>> = flow {
        emit(PokemonState.Loading)
        try {
            val networkPokemons = pokemonNetworkService.getPokemons("20", "0")
            val domainPokemons = ArrayList<Pokemon>()
            networkPokemons.pokemons.forEach { pokemon ->
                val pokemonInfo = pokemonNetworkService.getPokemonInfo(pokemon.url)
                val domainPokemon = networkMapper.mapNetworkToDomainPokemon(pokemon, pokemonInfo)

                pokemonInfo.abilities.forEach { ability ->
                    val domainAbility = networkMapper.mapNetworkToDomainAbility(ability)
                    val domainPokemonAbility =
                        networkMapper.mapNetworkToDomainPokemonWithAbilities(ability, pokemon)

                    pokemonDao.insertAbility(databaseMapper.mapDomainToDatabase(domainAbility))
                    pokemonDao.insertPokemonAbilityCrossRef(
                        databaseMapper.mapDomainToDatabase(
                            domainPokemon,
                            domainAbility
                        )
                    )
                }

                pokemonDao.insertPokemon(databaseMapper.mapDomainToDatabase(domainPokemon))
                domainPokemons.add(domainPokemon)
            }

            emit(PokemonState.Success(domainPokemons))

        } catch (networkException: Exception) {
            Log.e(
                "PokemonRepository",
                "getNetworkPokemon failed with networkException: $networkException"
            )
            try {
                val databasePokemons = pokemonDao.getAllPokemons()
                val domainPokemons =
                    databaseMapper.mapDatabaseListToDomainPokemonList(databasePokemons)

                emit(PokemonState.Success(domainPokemons))
            } catch (databaseException: Exception) {
                Log.e(
                    "PokemonRepository",
                    "getNetworkPokemon failed with networkException: $databaseException"
                )
                emit(PokemonState.Error("Network Error"))
            }
        }
    }

    override suspend fun getDatabasePokemonWithAbilities(name: String): Flow<PokemonInfoState<List<PokemonWIthAbilities>>> =
        flow {
            emit(PokemonInfoState.Loading)
            try {
                val databasePokemonsWithAbilities = pokemonDao.getPokemonWithAbilitiesById(name)
                val domainPokemonsWithAbilities = databaseMapper.mapDatabaseListToDomainPokemonWithAbilitiesList(databasePokemonsWithAbilities)
                emit(PokemonInfoState.Success(domainPokemonsWithAbilities))
            } catch (databaseException: Exception) {
                Log.e(
                    "PokemonRepository",
                    "getDatabasePokemonWithAbilities failed with databaseException: $databaseException"
                )
                emit(PokemonInfoState.Error("Network Error"))
            }
        }

}