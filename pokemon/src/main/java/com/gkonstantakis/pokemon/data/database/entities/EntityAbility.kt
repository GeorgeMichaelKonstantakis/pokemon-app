package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abilities")
data class EntityAbility(
    @PrimaryKey
    @ColumnInfo(name = "ability_name")
    var abilityName: String
) {
}