package com.gkonstantakis.pokemon.data.network

import com.gkonstantakis.pokemon.data.network.models.NetworkPokemonInfo
import com.gkonstantakis.pokemon.data.network.models.NetworkPokemons
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonNetworkService {

    @GET("pokemon")
    @Headers("Content-Type: application/json","Accept: application/json")
    suspend fun getPokemons(): NetworkPokemons

    @GET
    @Headers("Content-Type: application/json","Accept: application/json")
    suspend fun getPokemons(@Url url: String): NetworkPokemons

    @GET
    @Headers("Content-Type: application/json","Accept: application/json")
    suspend fun getPokemonInfo(@Url url: String): NetworkPokemonInfo
}