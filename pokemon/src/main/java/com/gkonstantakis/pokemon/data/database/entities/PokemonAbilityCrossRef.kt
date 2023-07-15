package com.gkonstantakis.pokemon.data.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["pokemon_name", "ability_name"])
data class PokemonAbilityCrossRef(
    val pokemonName: String,
    val abilityName: String
) {

}
