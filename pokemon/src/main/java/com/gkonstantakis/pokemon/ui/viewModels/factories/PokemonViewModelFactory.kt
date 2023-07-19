package com.gkonstantakis.pokemon.ui.viewModels.factories

import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel

class PokemonViewModelFactory(private val pokemonRepository: PokemonRepository) : Factory<PokemonViewModel>  {
    override fun create(): PokemonViewModel {
        return PokemonViewModel(pokemonRepository)
    }

}