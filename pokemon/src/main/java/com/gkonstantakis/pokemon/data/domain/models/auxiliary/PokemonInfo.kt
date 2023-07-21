package com.gkonstantakis.pokemon.data.domain.models.auxiliary

import com.gkonstantakis.pokemon.data.domain.models.Ability

data class PokemonInfo(
    var baseExperience: Int,
    var height: Int,
    var weight: Int,
    var image: String,
    var abilities: List<Ability>
)
