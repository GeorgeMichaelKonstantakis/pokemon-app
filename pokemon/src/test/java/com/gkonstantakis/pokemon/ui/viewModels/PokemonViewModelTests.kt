package com.gkonstantakis.pokemon.ui.viewModels

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.gkonstantakis.pokemon.data.domain.models.Pokemon
import com.gkonstantakis.pokemon.data.repositories.PokemonRepository
import com.gkonstantakis.pokemon.data.repositories.TestPokemonRepositoryImpl
import com.gkonstantakis.pokemon.data.state.PokemonState
import com.gkonstantakis.pokemon.ui.mappers.UiMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class PokemonViewModelTests {

    private lateinit var pokemonRepository: PokemonRepository
    private lateinit var pokemonViewModel: PokemonViewModel

    private var handler = Mockito.mock(Handler::class.java)

    @Before
    fun setup() {
        pokemonRepository = TestPokemonRepositoryImpl()
        pokemonViewModel = PokemonViewModel(pokemonRepository)
    }

    @Test
    fun setStateEvent() {

        pokemonViewModel.vMScope = CoroutineScope(Dispatchers.Default)
        pokemonViewModel.defaultScope = CoroutineScope(Dispatchers.Default)


        val lifecycleOwner: LifecycleOwner = Mockito.mock(LifecycleOwner::class.java)
        pokemonViewModel.pokemonState.observe(lifecycleOwner, Observer { pokemonState ->
            when (pokemonState) {
                is PokemonState.SuccessPokemon<List<Pokemon>> -> {
                    val pokemonList =
                        UiMapper().mapDomainToUIPokemonList(pokemonState.data)
                    pokemonList.forEach {
                        Log.e("pokemon: ", "pokemon: " + it.name)
                        Log.e("pokemon: ", "pokemon: " + it.image)
                    }
                }
            }
        })
        runBlocking {
            pokemonViewModel.setStateEvent(PokemonViewModel.StateEvent.GetPokemons)
        }
    }
}