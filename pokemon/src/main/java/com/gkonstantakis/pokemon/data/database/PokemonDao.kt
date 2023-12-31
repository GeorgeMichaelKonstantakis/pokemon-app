package com.gkonstantakis.pokemon.data.database

import androidx.room.*
import com.gkonstantakis.pokemon.data.database.entities.DatabaseAbility
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemon
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemonPaging
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef
import com.gkonstantakis.pokemon.data.database.entities.joins.JoinPokemonWithAbilities

@Dao
interface PokemonDao {

    @Transaction
    @Query("SELECT * FROM POKEMONS WHERE POKEMONNAME = :name")
    fun getPokemonWithAbilitiesByName(name: String): List<JoinPokemonWithAbilities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(databasePokemon: DatabasePokemon): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(databaseAbility: DatabaseAbility): Long

    @Query("SELECT * FROM POKEMONS")
    suspend fun getAllPokemons(): List<DatabasePokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonAbilityCrossRef(pokemonAbilityCrossRef: PokemonAbilityCrossRef): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePaging(databasePokemonPaging: DatabasePokemonPaging): Long

    @Query("SELECT * FROM POKEMON_PAGING")
    suspend fun getPaging(): List<DatabasePokemonPaging>
}