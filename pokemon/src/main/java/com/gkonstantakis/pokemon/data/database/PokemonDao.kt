package com.gkonstantakis.pokemon.data.database

import androidx.room.*
import com.gkonstantakis.pokemon.data.database.entities.Ability
import com.gkonstantakis.pokemon.data.database.entities.Pokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef
import com.gkonstantakis.pokemon.data.database.entities.joins.PokemonWithAbilities

@Dao
interface PokemonDao {

    @Transaction
    @Query("SELECT * FROM POKEMONS WHERE POKEMONID = :id")
    fun getPokemonWithAbilitiesById(id: Int): List<PokemonWithAbilities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: Pokemon): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(ability: Ability): Long

    @Query("SELECT * FROM POKEMONS")
    suspend fun getAllPokemons(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonAbilityCrossRef(pokemonAbilityCrossRef: PokemonAbilityCrossRef): Long
}