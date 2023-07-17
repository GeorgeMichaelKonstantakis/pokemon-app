package com.gkonstantakis.pokemon

import android.app.Application
import androidx.room.Room
import com.gkonstantakis.pokemon.data.config.BuildConfiguration
import com.gkonstantakis.pokemon.data.database.PokemonDao
import com.gkonstantakis.pokemon.data.database.PokemonDatabase
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.mappers.NetworkMapper
import com.gkonstantakis.pokemon.data.network.PokemonNetworkService
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.repositories.impl.PokemonRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApplication : Application() {
    lateinit var pokemonDB: PokemonDatabase
    lateinit var pokemonDao: PokemonDao
    lateinit var pokemonNetworkService: PokemonNetworkService
    lateinit var pokemonRepository: PokemonRepository

    override fun onCreate() {
        super.onCreate()

        pokemonDB = Room
            .databaseBuilder(
                applicationContext,
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

        initializeModuleApplication()

    }

    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    fun provideNetwork(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfiguration.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    fun initializeModuleApplication() {
        ModuleApplication.pokemonDB = pokemonDB
        ModuleApplication.pokemonDao = pokemonDao
        ModuleApplication.pokemonNetworkService = pokemonNetworkService
        ModuleApplication.pokemonRepository = pokemonRepository
    }
}