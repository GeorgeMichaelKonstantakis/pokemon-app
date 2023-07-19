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

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer()
        appContainer.init(applicationContext)

        initializeModuleApplication()

    }

    fun initializeModuleApplication() {
        ModuleApplication.appContainer = appContainer
    }
}