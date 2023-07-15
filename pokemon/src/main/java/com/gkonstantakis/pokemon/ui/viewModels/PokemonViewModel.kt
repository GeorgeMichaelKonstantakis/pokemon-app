package com.gkonstantakis.pokemon.ui.viewModels

import androidx.lifecycle.ViewModel
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {



    sealed class StateEvent {
        object GetPokemons : StateEvent()

        object GetPokemonInfo : StateEvent()
    }
}