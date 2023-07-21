package com.gkonstantakis.pokemon.data.domain.logic.impl

import android.util.Log
import com.gkonstantakis.pokemon.data.config.BuildConfiguration
import com.gkonstantakis.pokemon.data.domain.logic.PokemonLogic
import com.gkonstantakis.pokemon.data.domain.models.Paging
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonAbility
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.ArrayList

class PokemonLogicImpl(
    private val pokemonRepository: PokemonRepository
) : PokemonLogic {
    /**
     * Downloads the first 20 pokemon from the PokeAPI, and for each pokemon,
     * downloads the necessary info for it (image url, stats etc). Then this method saves the necessary
     * entities in the database, and emits the domain Pokemon list. If network request fails, it
     * fetches all the pokemon from the sqlite, if there are any.
     */
    override suspend fun getNetworkPokemon(): Flow<PokemonState<List<Pokemon>>> = flow {
        emit(PokemonState.Loading)
        try {
            val networkPokemons = pokemonRepository.getNetworkPokemon(BuildConfiguration.BASE_URL)
            val domainPokemons = ArrayList<Pokemon>()
            networkPokemons.pokemonWithNoInfoList.forEach { pokemon ->
                val pokemonInfo = pokemonRepository.getNetworkPokemonInfo(pokemon.pokemonUrl)

                pokemonInfo.abilities.forEach { ability ->

                    pokemonRepository.insertAbilityToDatabase(ability)

                    pokemonRepository.insertPokemonAbilityCrossRefToDatabase(
                        PokemonAbility(
                            pokemonName = pokemon.name,
                            abilityName = ability.name
                        )
                    )
                }

                val domainPokemon = Pokemon(
                    name = pokemon.name,
                    baseExperience = pokemonInfo.baseExperience,
                    weight = pokemonInfo.weight,
                    height = pokemonInfo.height,
                    image = pokemonInfo.image
                )

                domainPokemons.add(domainPokemon)
                pokemonRepository.insertPokemonToDatabase(domainPokemon)
            }

            val paging = Paging(networkPokemons.paging)
            pokemonRepository.insertOrUpdatePagingToDatabase(paging)

            emit(PokemonState.SuccessPokemon(domainPokemons))

        } catch (networkException: Exception) {
            Log.e(
                "PokemonRepository",
                "getNetworkPokemon failed with networkException: $networkException"
            )
            emit(PokemonState.Error("network_pokemon_error"))
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
            val oldPaging = pokemonRepository.getDatabasePaging()
            val networkPokemons = pokemonRepository.getNetworkPokemon(oldPaging.pagingUrl)
            val domainPokemons = ArrayList<Pokemon>()
            networkPokemons.pokemonWithNoInfoList.forEach { pokemon ->
                val pokemonInfo = pokemonRepository.getNetworkPokemonInfo(pokemon.pokemonUrl)

                pokemonInfo.abilities.forEach { ability ->

                    pokemonRepository.insertAbilityToDatabase(ability)

                    pokemonRepository.insertPokemonAbilityCrossRefToDatabase(
                        PokemonAbility(
                            pokemonName = pokemon.name,
                            abilityName = ability.name
                        )
                    )
                }

                val domainPokemon = Pokemon(
                    name = pokemon.name,
                    baseExperience = pokemonInfo.baseExperience,
                    weight = pokemonInfo.weight,
                    height = pokemonInfo.height,
                    image = pokemonInfo.image
                )

                domainPokemons.add(domainPokemon)
                pokemonRepository.insertPokemonToDatabase(domainPokemon)
            }

            val paging = Paging(networkPokemons.paging)
            pokemonRepository.insertOrUpdatePagingToDatabase(paging)

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
                val pokemonWithAbilities =
                    pokemonRepository.getDatabasePokemonWithAbilitiesByName(name)

                pokemonWithAbilities.first().abilities.forEach {
                    Log.e("LogicAbilkity", "LogicAbilkity: " + it.name)
                }

                emit(PokemonInfoState.Success(pokemonWithAbilities))
            } catch (databaseException: Exception) {
                Log.e(
                    "PokemonRepository",
                    "getDatabasePokemonWithAbilities failed with databaseException: $databaseException"
                )
                emit(PokemonInfoState.Error("Network Error"))
            }
        }

    override suspend fun getDatabasePokemon(): Flow<PokemonState<List<Pokemon>>> = flow {
        try {
            val domainPokemons = pokemonRepository.getDatabasePokemons()

            emit(PokemonState.SuccessDatabasePokemon(domainPokemons))
        } catch (databaseException: Exception) {
            Log.e(
                "PokemonRepository",
                "getDatabaseError failed with databaseException: $databaseException"
            )
            emit(PokemonState.Error("Database Error"))
        }
    }
}