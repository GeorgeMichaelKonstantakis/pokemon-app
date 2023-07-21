package com.gkonstantakis.pokemon.data.domain.models.auxiliary

data class PokemonWithNoInfoAndPaging(
    var pokemonWithNoInfoList: List<PokemonWithNoInfo>,
    var paging: String
)
