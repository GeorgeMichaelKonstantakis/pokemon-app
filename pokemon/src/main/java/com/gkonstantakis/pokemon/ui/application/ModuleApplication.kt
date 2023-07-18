package com.gkonstantakis.pokemon.ui.application

import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.database.PokemonDatabase
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository

object ModuleApplication {

    lateinit var pokemonDB: PokemonDatabase
    lateinit var pokemonDao: PokemonDao
    lateinit var pokemonNetworkService: PokemonNetworkService
    lateinit var pokemonRepository: PokemonRepository
}