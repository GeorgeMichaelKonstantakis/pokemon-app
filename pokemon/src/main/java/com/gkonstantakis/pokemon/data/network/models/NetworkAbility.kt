package com.gkonstantakis.pokemon.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkAbility(
    @SerializedName("name")
    @Expose
    var name: String
) {
}