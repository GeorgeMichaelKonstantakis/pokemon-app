package com.gkonstantakis.pokemon.data.database.entities.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.gkonstantakis.pokemon.data.database.entities.EntityAbility
import com.gkonstantakis.pokemon.data.database.entities.EntityPokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

data class JoinPokemonWithAbilities(
    @Embedded val entityPokemon: EntityPokemon,
    @Relation(
        parentColumn = "pokemon_name",
        entityColumn = "ability_name",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val entityAbilities: List<EntityAbility>
) {

}
