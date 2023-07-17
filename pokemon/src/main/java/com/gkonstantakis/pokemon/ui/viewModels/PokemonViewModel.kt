package com.gkonstantakis.pokemon.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkonstantakis.pokemon.ModuleApplication
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel() : ViewModel() {

    private val _pokemonState: MutableLiveData<PokemonState<List<Pokemon>>> =
        MutableLiveData()

    val pokemonState: LiveData<PokemonState<List<Pokemon>>>
        get() = _pokemonState

    private val _pokemonInfoState: MutableLiveData<PokemonInfoState<List<PokemonWIthAbilities>>> =
        MutableLiveData()

    val pokemonInfoState: LiveData<PokemonInfoState<List<PokemonWIthAbilities>>>
        get() = _pokemonInfoState

    fun setStateEvent(stateEvent: StateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                is StateEvent.GetPokemons -> {
                    ModuleApplication.pokemonRepository.getNetworkPokemon().onEach { pokemonState ->
                        _pokemonState.value = pokemonState
                    }.launchIn(viewModelScope)
                }
                is StateEvent.GetPagingPokemons -> {
                    ModuleApplication.pokemonRepository.getNetworkPagingPokemon()
                        .onEach { pokemonState ->
                            _pokemonState.value = pokemonState
                        }.launchIn(viewModelScope)
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (stateEvent) {
                is StateEvent.GetPokemonInfo -> {
                    withContext(Dispatchers.IO) {
                        ModuleApplication.pokemonRepository.getDatabasePokemonWithAbilities(
                            stateEvent.pokemonName
                        )
                            .onEach { pokemonInfoState ->
                                _pokemonInfoState.value = pokemonInfoState
                            }.launchIn(viewModelScope)
                    }
                }
            }
        }


    }

    sealed class StateEvent {
        object GetPokemons : StateEvent()

        object GetPagingPokemons : StateEvent()

        data class GetPokemonInfo(var pokemonName: String) : StateEvent()
    }
}