package com.gkonstantakis.pokemon.data.database.entities.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.gkonstantakis.pokemon.data.database.entities.DatabaseAbility
import com.gkonstantakis.pokemon.data.database.entities.DatabasePokemon
import com.gkonstantakis.pokemon.data.database.entities.PokemonAbilityCrossRef

data class JoinPokemonWithAbilities(
    @Embedded val databasePokemon: DatabasePokemon,
    @Relation(
        parentColumn = "pokemonName",
        entity = DatabaseAbility::class,
        entityColumn = "abilityName",
        associateBy = Junction(
            value = PokemonAbilityCrossRef::class,
            parentColumn = "pokemonName",
            entityColumn = "abilityName")
    )
    val databaseAbilities: List<DatabaseAbility>
) {

}
