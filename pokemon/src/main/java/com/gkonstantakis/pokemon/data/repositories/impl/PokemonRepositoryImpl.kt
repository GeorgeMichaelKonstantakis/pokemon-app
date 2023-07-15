package com.gkonstantakis.pokemon.data.repositories.impl

import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository

class PokemonRepositoryImpl(
    private val pokemonNetworkService: PokemonNetworkService,
    private val pokemonDao: PokemonDao,
    private val databaseMapper: DatabaseMapper,
    private val networkMapper: NetworkMapper
) : PokemonRepository {
}