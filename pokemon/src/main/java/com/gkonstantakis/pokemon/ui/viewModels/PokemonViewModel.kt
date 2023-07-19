package com.gkonstantakis.pokemon.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    var vMScope = viewModelScope
    var defaultScope = CoroutineScope(Dispatchers.Default)

    private val _pokemonState: MutableLiveData<PokemonState<List<Pokemon>>> =
        MutableLiveData()

    val pokemonState: LiveData<PokemonState<List<Pokemon>>>
        get() = _pokemonState

    private val _pokemonInfoState: MutableLiveData<PokemonInfoState<List<PokemonWIthAbilities>>> =
        MutableLiveData()

    val pokemonInfoState: LiveData<PokemonInfoState<List<PokemonWIthAbilities>>>
        get() = _pokemonInfoState

    fun setStateEvent(stateEvent: StateEvent) {
        vMScope.launch {
            when (stateEvent) {
                is StateEvent.GetPokemons -> {
                    pokemonRepository.getNetworkPokemon().onEach { pokemonState ->
                        _pokemonState.postValue(pokemonState)
                    }.launchIn(viewModelScope)
                }
                is StateEvent.GetPagingPokemons -> {
                    pokemonRepository.getNetworkPagingPokemon()
                        .onEach { pokemonState ->
                            _pokemonState.postValue(pokemonState)
                        }.launchIn(vMScope)
                }
            }
        }

        defaultScope.launch {
            when (stateEvent) {
                is StateEvent.GetPokemonInfo -> {
                    withContext(Dispatchers.IO) {
                        pokemonRepository.getDatabasePokemonWithAbilities(
                            stateEvent.pokemonName
                        )
                            .onEach { pokemonInfoState ->
                                _pokemonInfoState.postValue(pokemonInfoState)
                            }.launchIn(defaultScope)
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