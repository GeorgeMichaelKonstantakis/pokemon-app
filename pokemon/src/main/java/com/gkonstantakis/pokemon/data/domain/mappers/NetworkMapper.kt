package com.gkonstantakis.pokemon.data.domain.mappers

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.network.models.NetworkPokemonInfo
import com.gkonstantakis.pokemon.data.network.models.inner.NetworkAbility
import com.gkonstantakis.pokemon.data.network.models.inner.NetworkPokemon

class NetworkMapper {
    fun mapNetworkToDomainPokemon(networkPokemons: NetworkPokemon,networkPokemonInfo: NetworkPokemonInfo): Pokemon {
        return Pokemon(
            name = networkPokemons.name,
            baseExperience = networkPokemonInfo.baseExperience,
            height = networkPokemonInfo.height,
            image = networkPokemonInfo.sprites.image,
            weight =networkPokemonInfo.weight
        )
    }

    fun mapNetworkToDomainAbility(networkAbility: NetworkAbility): Ability {
        return Ability(
            name = networkAbility.ability.name
        )
    }

    fun mapNetworkToDomainPokemonWithAbilities(networkAbility: NetworkAbility, networkPokemon: NetworkPokemon): Ability {
        return Ability(
            name = networkAbility.ability.name
        )
    }

}