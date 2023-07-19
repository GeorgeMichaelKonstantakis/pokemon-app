package com.gkonstantakis.pokemon.data.domain.models

data class PokemonWIthAbilities(
    val pokemon: Pokemon,
    val abilities: List<Ability>
) {}