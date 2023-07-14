package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonID", "abilityID"])
data class PokemonAbilityCrossRef(
    val pokemonID: Int,
    val abilityID: Int
) {

}
