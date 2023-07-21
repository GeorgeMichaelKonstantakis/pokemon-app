package com.gkonstantakis.pokemon

import android.content.Context
import androidx.room.Room
import com.gkonstantakis.pokemon.data.config.BuildConfiguration
import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.database.PokemonDatabase
import com.gkonstantakis.pokemon.data.domain.logic.PokemonLogic
import com.gkonstantakis.pokemon.data.domain.logic.impl.PokemonLogicImpl
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.repositories.impl.PokemonRepositoryImpl
import com.gkonstantakis.pokemon.ui.viewModels.factories.PokemonViewModelFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private lateinit var pokemonDB: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao
    private lateinit var pokemonNetworkService: PokemonNetworkService

    lateinit var pokemonRepository: PokemonRepository
    lateinit var pokemonLogic: PokemonLogic
    lateinit var pokemonViewModelFactory: PokemonViewModelFactory
    lateinit var appContext: Context

    fun init(context: Context) {
        pokemonDB = Room
            .databaseBuilder(
                context,
                PokemonDatabase::class.java,
                PokemonDatabase.POKEMON_DB
            )
            .build()

        pokemonDao = (pokemonDB as PokemonDatabase).pokemonDao()

        pokemonNetworkService =
            provideGsonBuilder().let {
                provideNetwork(it).build().create(PokemonNetworkService::class.java)
            }

        pokemonRepository =
            PokemonRepositoryImpl(
                pokemonNetworkService,
                pokemonDao, DatabaseMapper(), NetworkMapper()
            )

        pokemonLogic = PokemonLogicImpl(pokemonRepository)

        pokemonViewModelFactory = PokemonViewModelFactory(pokemonLogic)

        appContext = context
    }


    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    fun provideNetwork(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfiguration.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}