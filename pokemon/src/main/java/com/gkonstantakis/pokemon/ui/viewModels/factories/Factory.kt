package com.gkonstantakis.pokemon.ui.viewModels.factories

interface Factory<T> {
    fun create(): T
}