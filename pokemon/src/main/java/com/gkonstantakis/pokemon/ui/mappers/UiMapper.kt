package com.gkonstantakis.pokemon.ui.mappers

import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonWithAbilitiesItem

class UiMapper {

    fun mapDomainToUIPokemonList(pokemons: List<Pokemon>): List<PokemonAdapterItem> {
        return pokemons.map {
            mapDomainToUI(it)
        }
    }

    fun mapDomainToUIPokemonWithAbilitiesList(pokemonsWithAbilities: List<PokemonWIthAbilities>): List<PokemonWithAbilitiesItem> {
        return pokemonsWithAbilities.map {
            mapDomainToUI(it)
        }
    }

    fun mapDomainToUI(pokemon: Pokemon): PokemonAdapterItem {
        return PokemonAdapterItem(
            name = pokemon.name,
            image = pokemon.image
        )
    }

    fun mapDomainToUI(pokemonWithAbilities: PokemonWIthAbilities): PokemonWithAbilitiesItem {
        val abilities = ArrayList<String>()
        pokemonWithAbilities.abilities.forEach { ability ->
            abilities.add(ability.name)
        }
        val pokemon = pokemonWithAbilities.pokemon
        return PokemonWithAbilitiesItem(
            name = pokemon.name,
            baseExperience = pokemon.baseExperience,
            weight = pokemon.weight,
            height = pokemon.height,
            abilities = abilities
        )
    }

}