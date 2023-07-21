package com.gkonstantakis.pokemon.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gkonstantakis.pokemon.ModuleApplication
import com.gkonstantakis.pokemon.data.domain.logic.PokemonLogic
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.domain.models.PokemonWIthAbilities
import com.gkonstantakis.pokemon.data.state.PokemonInfoState
import com.gkonstantakis.pokemon.data.state.PokemonState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val pokemonLogic: PokemonLogic) : ViewModel() {

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
                    pokemonLogic.getNetworkPokemon().onEach { pokemonState ->
                        _pokemonState.postValue(pokemonState)
                    }.launchIn(viewModelScope)
                }
                is StateEvent.GetPagingPokemons -> {
                    pokemonLogic.getNetworkPagingPokemon()
                        .onEach { pokemonState ->
                            _pokemonState.postValue(pokemonState)
                        }.launchIn(vMScope)
                }
                is StateEvent.GetDatabasePokemons -> {
                    pokemonLogic.getDatabasePokemon()
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
                        pokemonLogic.getDatabasePokemonWithAbilities(
                            stateEvent.pokemonName
                        )
                            .onEach { pokemonInfoState ->
                                _pokemonInfoState.postValue(pokemonInfoState)
                            }.launchIn(defaultScope)
                    }
                }
                is StateEvent.LoadPokemonImages -> {
                    withContext(Dispatchers.IO) {
                        stateEvent.pokemonList.forEach {
                            Glide.with(ModuleApplication.appContainer.appContext)
                                .load(it.image)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .preload();
                        }
                    }
                }
            }
        }
    }

    sealed class StateEvent {
        object GetPokemons : StateEvent()

        object GetPagingPokemons : StateEvent()

        object GetDatabasePokemons : StateEvent()

        data class LoadPokemonImages(var pokemonList: List<Pokemon>) : StateEvent()

        data class GetPokemonInfo(var pokemonName: String) : StateEvent()
    }
}