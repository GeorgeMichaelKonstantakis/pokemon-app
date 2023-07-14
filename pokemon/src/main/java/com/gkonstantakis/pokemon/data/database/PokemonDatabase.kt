package com.gkonstantakis.pokemon.data.database

import androidx.room.Database
import com.gkonstantakis.pokemon.data.database.entities.Ability
import com.gkonstantakis.pokemon.data.database.entities.Pokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

@Database(entities = [Pokemon::class, Ability::class, PokemonAbilityCrossRef::class], version = 1)
abstract class PokemonDatabase {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val POKEMON_DB: String = "pokemon_db"
    }
}