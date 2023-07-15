package com.gkonstantakis.pokemon.data.network.models

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkPokemonInfo(
    @SerializedName("abilities")
    @Expose
    var name: List<Ability>
) {}
