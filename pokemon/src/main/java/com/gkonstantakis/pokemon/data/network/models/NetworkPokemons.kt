package com.gkonstantakis.pokemon.data.network.models

import com.gkonstantakis.pokemon.data.network.models.inner.NetworkPokemon
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkPokemons(
    @SerializedName("next")
    @Expose
    var next: String,
    @SerializedName("previous")
    @Expose
    var previous: String,
    @SerializedName("results")
    @Expose
    var pokemons: List<NetworkPokemon>
) {}
