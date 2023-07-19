package com.gkonstantakis.pokemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gkonstantakis.pokemon.data.database.entities.DatabaseAbility
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemon
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemonPaging
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

@Database(entities = [DatabasePokemon::class, DatabaseAbility::class, PokemonAbilityCrossRef::class, DatabasePokemonPaging::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val POKEMON_DB: String = "pokemon_db"
    }
}