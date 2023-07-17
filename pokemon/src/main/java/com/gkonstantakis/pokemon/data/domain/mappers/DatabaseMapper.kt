package com.gkonstantakis.pokemon.data.domain.mappers

import com.gkonstantakis.pokemon.data.database.entities.DatabaseAbility
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef
import com.gkonstantakis.pokemon.data.database.entities.joins.JoinPokemonWithAbilities
import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities

class DatabaseMapper {

    fun mapDatabaseToDomain(databasePokemon: DatabasePokemon): Pokemon {
        return Pokemon(
            name = databasePokemon.pokemonName,
            baseExperience = databasePokemon.baseExperience,
            height = databasePokemon.height,
            image = databasePokemon.image,
            weight = databasePokemon.weight
        )
    }

    fun mapDatabaseToDomain(databaseAbility: DatabaseAbility): Ability {
        return Ability(
            name = databaseAbility.abilityName
        )
    }

    fun mapDatabaseToDomain(joinPokemonWithAbilities: JoinPokemonWithAbilities): PokemonWIthAbilities {
        return PokemonWIthAbilities(
            pokemon = mapDatabaseToDomain(joinPokemonWithAbilities.databasePokemon),
            abilities = mapDatabaseToDomainAbilitiesList(joinPokemonWithAbilities.databaseAbilities)
        )
    }

    fun mapDomainToDatabase(pokemon: Pokemon): DatabasePokemon {
        return DatabasePokemon(
            pokemonName = pokemon.name,
            height = pokemon.height,
            baseExperience = pokemon.baseExperience,
            image = pokemon.image,
            weight = pokemon.weight
        )
    }

    fun mapDomainToDatabase(ability: Ability): DatabaseAbility {
        return DatabaseAbility(
            abilityName = ability.name
        )
    }

    fun mapDomainToDatabase(pokemon: Pokemon, ability: Ability): PokemonAbilityCrossRef {
        return PokemonAbilityCrossRef(
            abilityName = ability.name,
            pokemonName = pokemon.name
        )
    }

    fun mapDatabaseToDomainAbilitiesList(abilities: List<DatabaseAbility>): List<Ability> {
        return abilities.map {
            mapDatabaseToDomain(it)
        }
    }

    fun mapDatabaseListToDomainPokemonList(pokemons: List<DatabasePokemon>): List<Pokemon> {
        return pokemons.map {
            mapDatabaseToDomain(it)
        }
    }

    fun mapDatabaseListToDomainPokemonWithAbilitiesList(pokemonWithAbilities: List<JoinPokemonWithAbilities>): List<PokemonWIthAbilities> {
        return pokemonWithAbilities.map {
            mapDatabaseToDomain(it)
        }
    }

}