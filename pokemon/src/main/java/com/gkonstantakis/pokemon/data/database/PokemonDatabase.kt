package com.gkonstantakis.pokemon.data.database

import androidx.room.Database
import com.gkonstantakis.pokemon.data.database.entities.EntityAbility
import com.gkonstantakis.pokemon.data.database.entities.EntityPokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

@Database(entities = [EntityPokemon::class, EntityAbility::class, PokemonAbilityCrossRef::class], version = 1)
abstract class PokemonDatabase {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val POKEMON_DB: String = "pokemon_db"
    }
}