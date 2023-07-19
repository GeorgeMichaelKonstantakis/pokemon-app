package com.gkonstantakis.pokemon.data.network.models

import com.gkonstantakis.pokemon.data.network.models.inner.NetworkAbility
import com.gkonstantakis.pokemon.data.network.models.inner.NetworkSprite
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkPokemonInfo(
    @SerializedName("abilities")
    @Expose
    var abilities: List<NetworkAbility>,
    @SerializedName("sprites")
    @Expose
    var sprites: NetworkSprite,
    @SerializedName("height")
    @Expose
    var height: Int,
    @SerializedName("weight")
    @Expose
    var weight: Int,
    @SerializedName("base_experience")
    @Expose
    var baseExperience: Int
) {}
