package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pokemonID")
    var pokemonID: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "base_experience")
    var baseExperience: Int,

    @ColumnInfo(name = "height")
    var height: Int,

    @ColumnInfo(name = "thumbnail")
    var smallImage: String,

    @ColumnInfo(name = "thumbnail")
    var largeImage: String
) {}
