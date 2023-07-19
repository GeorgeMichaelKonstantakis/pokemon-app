package com.gkonstantakis.pokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkonstantakis.pokemon.databinding.PokemonItemBinding
import com.gkonstantakis.pokemon.ui.models.PokemonAdapterItem
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel

class PokemonAdapter(
    private val viewModel: PokemonViewModel
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonItemViewHolder>() {

    inner class PokemonItemViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<PokemonAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: PokemonAdapterItem,
            newItem: PokemonAdapterItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: PokemonAdapterItem,
            newItem: PokemonAdapterItem
        ): Boolean {
            return oldItem.image == newItem.image
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

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
        val pokemonItem = differ.currentList[position]
        holder.binding.apply {
            val name = this.pokemonName
            val image = this.pokemonImage
            val detailsButton = this.pokemonDetailsButton

            name.text = pokemonItem.name

            detailsButton.setOnClickListener {
                viewModel.setStateEvent(PokemonViewModel.StateEvent.GetPokemonInfo(pokemonItem.name))
            }

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(pokemonItem)
                }
            }

            Glide.with(root.context).load(pokemonItem.image)
                .into(image);
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((PokemonAdapterItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (PokemonAdapterItem) -> Unit) {
        onItemClickListener = listener
    }
}