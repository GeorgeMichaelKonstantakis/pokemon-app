package com.gkonstantakis.pokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkonstantakis.pokemon.databinding.PokemonItemBinding
import com.gkonstantakis.pokemon.ui.activities.MainActivity
import com.gkonstantakis.pokemon.ui.mappers.UiMapper
import com.gkonstantakis.pokemon.ui.models.EventAdapterItem
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel

class PokemonAdapter(
    var pokemonItems: MutableList<PokemonAdapterItem>,
    val uiMapper: UiMapper,
    val pokemonViewModel: PokemonViewModel,
    private val activity: MainActivity
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonItemViewHolder>() {

    inner class PokemonItemViewHolder(var pokemonItemBinding: PokemonItemBinding) :
        RecyclerView.ViewHolder(pokemonItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        return PokemonItemViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        val binding = holder.pokemonItemBinding
        holder.itemView.apply {
            val pokemonItem = pokemonItems[holder.adapterPosition]

            val name = binding.pokemonName
            val image = binding.pokemonImage
            val detailsButton = binding.pokemonDetailsButton

            name.text = pokemonItem.name

            detailsButton.setOnClickListener {

            }

            Glide.with(context).load(pokemonItem.image)
                .apply(RequestOptions.circleCropTransform())
                .into(image);
        }
    }

    override fun getItemCount(): Int {
        return pokemonItems.size
    }
}