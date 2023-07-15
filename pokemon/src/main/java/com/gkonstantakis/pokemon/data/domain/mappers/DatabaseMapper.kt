package com.gkonstantakis.pokemon.data.domain.mappers

import com.gkonstantakis.pokemon.data.database.entities.EntityAbility
import com.gkonstantakis.pokemon.data.database.entities.EntityPokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef
import com.gkonstantakis.pokemon.data.database.entities.joins.JoinPokemonWithAbilities
import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities

class DatabaseMapper {

    fun mapDatabaseToDomain(entityPokemon: EntityPokemon): Pokemon {
        return Pokemon(
            name = entityPokemon.pokemonName,
            baseExperience = entityPokemon.baseExperience,
            height = entityPokemon.height,
            image = entityPokemon.image,
            weight = entityPokemon.weight
        )
    }

    fun mapDatabaseToDomain(entityAbility: EntityAbility): Ability {
        return Ability(
            name = entityAbility.abilityName
        )
    }

    fun mapDatabaseToDomain(joinPokemonWithAbilities: JoinPokemonWithAbilities): PokemonWIthAbilities {
        return PokemonWIthAbilities(
            pokemon = mapDatabaseToDomain(joinPokemonWithAbilities.entityPokemon),
            abilities = mapDatabaseToDomainAbilitiesList(joinPokemonWithAbilities.entityAbilities)
        )
    }

    fun mapDomainToDatabase(pokemon: Pokemon): EntityPokemon {
        return EntityPokemon(
            pokemonName = pokemon.name,
            height = pokemon.height,
            baseExperience = pokemon.baseExperience,
            image = pokemon.image,
            weight = pokemon.weight
        )
    }

    fun mapDomainToDatabase(ability: Ability): EntityAbility {
        return EntityAbility(
            abilityName = ability.name
        )
    }

    fun mapDomainToDatabase(pokemon: Pokemon, ability: Ability): PokemonAbilityCrossRef {
        return PokemonAbilityCrossRef(
            abilityName = ability.name,
            pokemonName = pokemon.name
        )
    }

    fun mapDatabaseToDomainAbilitiesList(abilities: List<EntityAbility>): List<Ability> {
        return abilities.map {
            mapDatabaseToDomain(it)
        }
    }

    fun mapDatabaseListToDomainPokemonList(pokemons: List<EntityPokemon>): List<Pokemon> {
        return pokemons.map {
            mapDatabaseToDomain(it)
        }
    }

}