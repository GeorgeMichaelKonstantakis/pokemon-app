package com.gkonstantakis.pokemon.ui.models

data class PokemonWithAbilitiesItem(
    var name: String,
    var baseExperience: Int,
    var weight: Int,
    var height: Int,
    var abilities: List<String>
) {}