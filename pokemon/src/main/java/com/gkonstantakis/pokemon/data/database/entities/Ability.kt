package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abilities")
data class Ability(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "abilityID")
    var abilityID: Int,

    @ColumnInfo(name = "name")
    var name: String
) {
}