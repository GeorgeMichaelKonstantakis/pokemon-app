package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class DatabasePokemon(
    @PrimaryKey
    @ColumnInfo(name = "pokemonName")
    var pokemonName: String,

    @ColumnInfo(name = "baseExperience")
    var baseExperience: Int,

    @ColumnInfo(name = "height")
    var height: Int,

    @ColumnInfo(name = "weight")
    var weight: Int,

    @ColumnInfo(name = "image")
    var image: String
) {}
