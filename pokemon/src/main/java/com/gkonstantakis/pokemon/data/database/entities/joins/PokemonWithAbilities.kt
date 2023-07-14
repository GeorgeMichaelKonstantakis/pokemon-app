package com.gkonstantakis.pokemon.data.database.entities.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.gkonstantakis.pokemon.data.database.entities.Ability
import com.gkonstantakis.pokemon.data.database.entities.Pokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

data class PokemonWithAbilities(
    @Embedded val pokemon: Pokemon,
    @Relation(
        parentColumn = "pokemonID",
        entityColumn = "abilityID",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val abilities: List<Ability>
) {

}
