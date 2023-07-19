package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_paging")
data class DatabasePokemonPaging(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "pagingUrl")
    val pagingUrl: String
) {}
