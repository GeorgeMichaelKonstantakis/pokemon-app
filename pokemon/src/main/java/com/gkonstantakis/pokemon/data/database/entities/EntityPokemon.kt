package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class EntityPokemon(
    @PrimaryKey
    @ColumnInfo(name = "pokemon_name")
    var pokemonName: String,

    @ColumnInfo(name = "base_experience")
    var baseExperience: Int,

    @ColumnInfo(name = "height")
    var height: Int,

    @ColumnInfo(name = "weight")
    var weight: Int,

    @ColumnInfo(name = "image")
    var image: String
) {}
