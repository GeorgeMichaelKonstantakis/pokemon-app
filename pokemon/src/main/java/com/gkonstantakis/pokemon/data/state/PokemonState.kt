package com.gkonstantakis.pokemon.data.state

sealed class PokemonState<out R> {
    data class Success<out T>(val data: T) : PokemonState<T>()

    data class Error(val message: String) : PokemonState<Nothing>()

    object Loading : PokemonState<Nothing>()
}
