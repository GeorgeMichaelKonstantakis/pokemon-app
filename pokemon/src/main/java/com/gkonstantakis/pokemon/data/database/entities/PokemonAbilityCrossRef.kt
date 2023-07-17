package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonName", "abilityName"])
data class PokemonAbilityCrossRef(
    val pokemonName: String,
    val abilityName: String
) {

}
