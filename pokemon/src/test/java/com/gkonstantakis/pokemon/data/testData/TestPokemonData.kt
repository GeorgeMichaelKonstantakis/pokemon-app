package com.gkonstantakis.pokemon.data.testData

import com.gkonstantakis.pokemon.data.domain.models.Ability
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities

object TestPokemonData {

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

    fun getPokemonsWithAbilities(): List<PokemonWIthAbilities> {
        val pokemonsWithAbilities = ArrayList<PokemonWIthAbilities>()

        val pokemon = Pokemon("nidorino", 128, 9, "nidorino", 195)
        val abilities = ArrayList<Ability>()
        abilities.add(
            Ability(
                name = "hustle"
            )
        )
        abilities.add(
            Ability(
                name = "poison-point"
            )
        )
        abilities.add(
            Ability(
                name = "rivarly"
            )
        )
        val pokemonWithAbilities = PokemonWIthAbilities(
            pokemon = pokemon,
            abilities = abilities
        )
        pokemonsWithAbilities.add(pokemonWithAbilities)
        return pokemonsWithAbilities
    }

    fun getPokemonWithAbilities(): PokemonWIthAbilities {
        val pokemon = Pokemon("nidorino", 128, 9, "nidorino", 195)
        val abilities = ArrayList<Ability>()
        abilities.add(
            Ability(
                name = "hustle"
            )
        )
        abilities.add(
            Ability(
                name = "poison-point"
            )
        )
        abilities.add(
            Ability(
                name = "rivarly"
            )
        )
        val pokemonWithAbilities = PokemonWIthAbilities(
            pokemon = pokemon,
            abilities = abilities
        )
        return pokemonWithAbilities
    }
}