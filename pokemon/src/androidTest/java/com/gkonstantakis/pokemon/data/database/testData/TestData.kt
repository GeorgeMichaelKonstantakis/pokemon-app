package com.gkonstantakis.pokemon.data.database.testData

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Paging
import com.gkonstantakis.pokemon.data.domain.models.Pokemon

object TestData {

    fun getTestPokemon(): Pokemon {
        return Pokemon(
            name = "Charizard",
            baseExperience = 100,
            weight = 100,
            height = 100,
            image = "charizard"
        )
    }

    fun getTestAbility(): Ability {
        return Ability(
            name = "blaze"
        )
    }

    fun getTestPaging(): Paging {
        return Paging(
            pagingUrl = "testUrl"
        )
    }

    fun getPokemonData(): List<Pokemon> {
        val pokemons = ArrayList<Pokemon>()

        pokemons.add(
            Pokemon("bulbasaur", 64, 7, "bulbasaur", 69)
        )

        pokemons.add(
            Pokemon("charmander", 62, 6, "charmander", 85)
        )

        pokemons.add(
            Pokemon("charizard", 267, 17, "charizard", 905)
        )

        pokemons.add(
            Pokemon("blastoise", 265, 16, "blastoise", 855)
        )

        pokemons.add(
            Pokemon("metapod", 72, 7, "metapod", 99)
        )

        pokemons.add(
            Pokemon("weedle", 39, 3, "weedle", 32)
        )

        pokemons.add(
            Pokemon("beedrill", 178, 10, "beedrill", 295)
        )

        pokemons.add(
            Pokemon("rattata", 51, 3, "rattata", 35)
        )

        pokemons.add(
            Pokemon("pikachu", 112, 4, "pikachu", 60)
        )

        pokemons.add(
            Pokemon("nidorino", 128, 9, "nidorino", 195)
        )

        return pokemons
    }

}