package com.gkonstantakis.pokemon.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.gkonstantakis.pokemon.data.database.testData.TestData
import com.gkonstantakis.pokemon.data.domain.mappers.DatabaseMapper
import com.gkonstantakis.pokemon.data.domain.models.auxiliary.PokemonAbility
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonDaoTests {

    private lateinit var pokemonDatabase: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    val databaseMapper = DatabaseMapper()

    @Before
    fun setup() {
        pokemonDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokemonDatabase::class.java
        ).allowMainThreadQueries().build()
        pokemonDao = pokemonDatabase.pokemonDao()
    }

    @Test
    fun testInsertPokemonToDatabase() {
        runBlocking {
            val pokemon = TestData.getTestPokemon()

            val databasePokemon = databaseMapper.mapDomainToDatabase(pokemon)

            pokemonDao.insertPokemon(databasePokemon)

            val databasePokemonsList = pokemonDao.getAllPokemons()

            assert(databasePokemonsList.contains(databasePokemon))
        }
    }

    @Test
    fun testGetPokemonWithAbilitiesByName() {
        runBlocking {
            val pokemon = TestData.getTestPokemon()

            val ability = TestData.getTestAbility()

            val pokemonAbility = PokemonAbility(
                pokemonName = pokemon.name,
                abilityName = ability.name
            )


            pokemonDao.insertPokemon(databaseMapper.mapDomainToDatabase(pokemon))
            pokemonDao.insertAbility(databaseMapper.mapDomainToDatabase(ability))
            pokemonDao.insertPokemonAbilityCrossRef(
                databaseMapper.mapDomainToDatabase(
                    pokemonAbility
                )
            )

            val pokemonWithAbilities = pokemonDao.getPokemonWithAbilitiesByName(pokemon.name)

            assert(pokemonWithAbilities.first().databasePokemon.pokemonName == pokemon.name)
            assert(pokemonWithAbilities.first().databaseAbilities.first().abilityName == ability.name)
        }
    }

    @Test
    fun testInsertOrUpdatePaging() {
        runBlocking {
            val paging = TestData.getTestPaging()
            val databasePaging = databaseMapper.mapDomainToDatabase(paging)
            pokemonDao.insertOrUpdatePaging(databasePaging)

            val pagings = pokemonDao.getPaging()

            assert(pagings.contains(databasePaging))
        }
    }

    @Test
    fun testGetPokemons() {
        runBlocking {
            var pokemonList = TestData.getPokemonData()
            var totalDomainPokemonWeight = 0
            pokemonList.forEach { pokemon ->
                pokemonDao.insertPokemon(databaseMapper.mapDomainToDatabase(pokemon))
                totalDomainPokemonWeight += pokemon.weight
            }

            var totalDatabasePokemonWeight = 0
            val databasePokemons = pokemonDao.getAllPokemons()
            databasePokemons.forEach { pokemon ->
                totalDatabasePokemonWeight += pokemon.weight
            }

            assert(totalDatabasePokemonWeight == totalDomainPokemonWeight)
        }
    }


    @After
    fun tearDown() {
        pokemonDatabase.close()
    }
}