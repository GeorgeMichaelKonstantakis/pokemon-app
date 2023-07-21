package com.gkonstantakis.pokemon.data.domain.mappers

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonInfo
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonWithNoInfo
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonWithNoInfoAndPaging
import com.gkonstantakis.pokemon.data.network.models.NetworkPokemonInfo
import com.gkonstantakis.pokemon.data.network.models.NetworkPokemons
import com.gkonstantakis.pokemon.data.network.models.inner.NetworkAbility
import com.gkonstantakis.pokemon.data.network.models.inner.NetworkPokemon

class NetworkMapper {
    fun mapNetworkToDomainPokemon(
        networkPokemons: NetworkPokemon,
        networkPokemonInfo: NetworkPokemonInfo
    ): Pokemon {
        return Pokemon(
            name = networkPokemons.name,
            baseExperience = networkPokemonInfo.baseExperience,
            height = networkPokemonInfo.height,
            image = networkPokemonInfo.sprites.image,
            weight = networkPokemonInfo.weight
        )
    }

    fun mapNetworkToDomainPokemonWithNoInfo(networkPokemon: NetworkPokemon): PokemonWithNoInfo {
        return PokemonWithNoInfo(
            name = networkPokemon.name,
            pokemonUrl = networkPokemon.url
        )
    }

    fun mapNetworkToDomainPokemonInfo(networkPokemonInfo: NetworkPokemonInfo): PokemonInfo {
        val abilities = ArrayList<Ability>()
        networkPokemonInfo.abilities.forEach {
            abilities.add(
                Ability(
                    name = it.ability.name
                )
            )
        }
        return PokemonInfo(
            baseExperience = networkPokemonInfo.baseExperience,
            height = networkPokemonInfo.height,
            image = networkPokemonInfo.sprites.image,
            weight = networkPokemonInfo.weight,
            abilities = abilities
        )
    }

    fun mapNetworkToDomainAbility(networkAbility: NetworkAbility): Ability {
        return Ability(
            name = networkAbility.ability.name
        )
    }

    fun mapNetworkToDomainPokemonWithAbilities(
        networkAbility: NetworkAbility,
        networkPokemon: NetworkPokemon
    ): Ability {
        return Ability(
            name = networkAbility.ability.name
        )
    }

    fun mapNetworkToDomainPokemonWithNoInfoList(networkPokemonList: List<NetworkPokemon>): List<PokemonWithNoInfo> {
        return networkPokemonList.map {
            mapNetworkToDomainPokemonWithNoInfo(it)
        }
    }

    fun mapNetworkToDomainPokemonWithNoInfoAndPaging(networkPokemons: NetworkPokemons): PokemonWithNoInfoAndPaging {
        return PokemonWithNoInfoAndPaging(
            paging = networkPokemons.next,
            pokemonWithNoInfoList = mapNetworkToDomainPokemonWithNoInfoList(networkPokemons.pokemons)
        )
    }

}