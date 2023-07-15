package com.gkonstantakis.pokemon.data.network.models.inner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkInnerAbility(
    @SerializedName("name")
    @Expose
    var name: String
) {}
