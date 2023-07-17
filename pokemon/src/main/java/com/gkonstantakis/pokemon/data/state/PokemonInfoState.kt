package com.gkonstantakis.pokemon.data.state

sealed class PokemonInfoState<out R> {

    data class Success<out T>(val data: T) : PokemonInfoState<T>()

    data class Error(val message: String) : PokemonInfoState<Nothing>()

    object Loading : PokemonInfoState<Nothing>()
}
