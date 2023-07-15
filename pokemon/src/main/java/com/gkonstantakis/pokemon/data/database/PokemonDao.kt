package com.gkonstantakis.pokemon.data.database

import androidx.room.*
import com.gkonstantakis.pokemon.data.database.entities.EntityAbility
import com.gkonstantakis.pokemon.data.database.entities.EntityPokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef
import com.gkonstantakis.pokemon.data.database.entities.joins.JoinPokemonWithAbilities

@Dao
interface PokemonDao {

    @Transaction
    @Query("SELECT * FROM POKEMONS WHERE POKEMON_NAME = :name")
    fun getPokemonWithAbilitiesById(name: String): List<JoinPokemonWithAbilities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(entityPokemon: EntityPokemon): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(entityAbility: EntityAbility): Long

    @Query("SELECT * FROM POKEMONS")
    suspend fun getAllPokemons(): List<EntityPokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonAbilityCrossRef(pokemonAbilityCrossRef: PokemonAbilityCrossRef): Long
}