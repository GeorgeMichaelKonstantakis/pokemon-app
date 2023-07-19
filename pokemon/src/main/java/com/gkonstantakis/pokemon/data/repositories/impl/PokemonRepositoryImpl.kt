package com.gkonstantakis.pokemon.data.repositories.impl

import android.util.Log
import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.domain.models.Paging
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

    /**
     * Downloads the first 20 pokemon from the PokeAPI, and for each pokemon,
     * downloads the necessary info for it (image url, stats etc). Then this method saves the necessary
     * entities in the database, and emits the domain Pokemon list. If network request fails, it
     * fetches all the pokemon from the sqlite, if there are any.
     */
    override suspend fun getNetworkPokemon(): Flow<PokemonState<List<Pokemon>>> = flow {
        emit(PokemonState.Loading)
        try {
            val networkPokemons = pokemonNetworkService.getPokemons()
            val domainPokemons = ArrayList<Pokemon>()
            networkPokemons.pokemons.forEach { pokemon ->
                val pokemonInfo = pokemonNetworkService.getPokemonInfo(pokemon.url)
                val domainPokemon = networkMapper.mapNetworkToDomainPokemon(pokemon, pokemonInfo)

                pokemonInfo.abilities.forEach { ability ->
                    val domainAbility = networkMapper.mapNetworkToDomainAbility(ability)

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

            val paging = Paging(networkPokemons.next)
            pokemonDao.insertOrUpdatePaging(databaseMapper.mapDomainToDatabase(paging))

            emit(PokemonState.SuccessPokemon(domainPokemons))

        } catch (networkException: Exception) {
            Log.e(
                "PokemonRepository",
                "getNetworkPokemon failed with networkException: $networkException"
            )
            try {
                val databasePokemons = pokemonDao.getAllPokemons()
                val domainPokemons =
                    databaseMapper.mapDatabaseListToDomainPokemonList(databasePokemons)

                emit(PokemonState.SuccessPokemon(domainPokemons))
            } catch (databaseException: Exception) {
                Log.e(
                    "PokemonRepository",
                    "getNetworkPokemon failed with networkException: $databaseException"
                )
                emit(PokemonState.Error("Network Error"))
            }
        }
    }

    /**
     * Downloads the next 20 pokemon data from the PokeAPI, according to the paging field value, and for each pokemon,
     * downloads the necessary info for it (image url, stats etc). Then this method saves the necessary
     * entities in the database, and emits the domain Pokemon list. If network request fails, it
     * fetches all the pokemon from the sqlite, if there are any.
     */
    override suspend fun getNetworkPagingPokemon(): Flow<PokemonState<List<Pokemon>>> = flow {
        emit(PokemonState.Loading)
        try {
            val oldPaging = pokemonDao.getPaging().first()
            val networkPokemons = pokemonNetworkService.getPokemons(oldPaging.pagingUrl)
            val domainPokemons = ArrayList<Pokemon>()
            networkPokemons.pokemons.forEach { pokemon ->
                val pokemonInfo = pokemonNetworkService.getPokemonInfo(pokemon.url)
                val domainPokemon = networkMapper.mapNetworkToDomainPokemon(pokemon, pokemonInfo)

                pokemonInfo.abilities.forEach { ability ->
                    val domainAbility = networkMapper.mapNetworkToDomainAbility(ability)

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

            val paging = Paging(networkPokemons.next)
            pokemonDao.insertOrUpdatePaging(databaseMapper.mapDomainToDatabase(paging))

            emit(PokemonState.SuccessPagingPokemon(domainPokemons))

        } catch (networkException: Exception) {
            Log.e(
                "PokemonRepository",
                "getNetworkPagingPokemon failed with networkException: $networkException"
            )
            emit(PokemonState.Error("Network Error"))
        }
    }

    /**
     * Query the database in order to fetch the required pokemon and a list of its' abilities into
     * a single object.
     */
    override suspend fun getDatabasePokemonWithAbilities(name: String): Flow<PokemonInfoState<List<PokemonWIthAbilities>>> =
        flow {
            try {
                val databasePokemonsWithAbilities = pokemonDao.getPokemonWithAbilitiesByName(name)
                val domainPokemonsWithAbilities =
                    databaseMapper.mapDatabaseListToDomainPokemonWithAbilitiesList(
                        databasePokemonsWithAbilities
                    )
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