package com.gkonstantakis.pokemon.ui.mappers

import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.testData.TestPokemonData
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonWithAbilitiesItem
import org.junit.Before
import org.junit.Test

class UiMapperTests {
    lateinit var pokemonList: List<Pokemon>
    lateinit var pokemonItemList: List<PokemonAdapterItem>
    lateinit var pokemonWIthAbilitiesItemList: List<PokemonWithAbilitiesItem>
    lateinit var pokemonWithAbilities: List<PokemonWIthAbilities>
    val uiMapper = UiMapper()

    @Before
    fun setup() {
        pokemonList = TestPokemonData.getPokemonData()
        pokemonWithAbilities = TestPokemonData.getPokemonsWithAbilities()
        pokemonWIthAbilitiesItemList = TestPokemonData.getPokemonsWithAbilitiesItems()
        pokemonItemList = TestPokemonData.getPokemonAdapterItemData()
    }

    @Test
    fun testUiMappingDomainToUiPokemonList() {
        val uiPokemonList = uiMapper.mapDomainToUIPokemonList(pokemonList)
        uiPokemonList.forEachIndexed { index, pokemonAdapterItem ->
            assert(pokemonAdapterItem.name == pokemonItemList[index].name)
            assert(pokemonAdapterItem.image == pokemonItemList[index].image)
        }
    }

    @Test
    fun testUiMappingDomainToUiPokemonWithAbilitiesList() {
        val uiPokemonWithAbilitiesList =
            uiMapper.mapDomainToUIPokemonWithAbilitiesList(pokemonWithAbilities)
        uiPokemonWithAbilitiesList.forEachIndexed { index, pokemonWithAbilitiesItem ->
            val pokemonWIthAbilitiesItemToCompare = pokemonWIthAbilitiesItemList[index]
            assert(pokemonWithAbilitiesItem.name == pokemonWIthAbilitiesItemToCompare.name)
            assert(pokemonWithAbilitiesItem.baseExperience == pokemonWIthAbilitiesItemToCompare.baseExperience)
            assert(pokemonWithAbilitiesItem.weight == pokemonWIthAbilitiesItemToCompare.weight)
            assert(pokemonWithAbilitiesItem.height == pokemonWIthAbilitiesItemToCompare.height)
            pokemonWithAbilitiesItem.abilities.forEachIndexed { index1, ability ->
                assert(ability == pokemonWIthAbilitiesItemToCompare.abilities[index1])
            }
        }
    }
}