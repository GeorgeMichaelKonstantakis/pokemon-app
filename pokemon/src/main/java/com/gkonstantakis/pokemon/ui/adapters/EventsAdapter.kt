package com.gkonstantakis.pokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.gkonstantakis.pokemon.R
import com.gkonstantakis.pokemon.databinding.EventItemBinding
import com.gkonstantakis.pokemon.ui.activities.MainActivity
import com.gkonstantakis.pokemon.ui.models.EventAdapterItem
import com.gkonstantakis.pokemon.ui.viewModels.PokemonViewModel


class EventsAdapter(
    var eventItems: MutableList<EventAdapterItem>,
    val pokemonViewModel: PokemonViewModel,
    private val activity: MainActivity
) :
    RecyclerView.Adapter<EventsAdapter.EventItemViewHolder>() {

    inner class EventItemViewHolder(var eventItemBinding: EventItemBinding) :
        RecyclerView.ViewHolder(eventItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        return EventItemViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val binding = holder.eventItemBinding
        holder.itemView.apply {
            val eventItem = eventItems[holder.adapterPosition]

            val image = binding.eventImage
            val subtitle = binding.eventSubtitle
            val date = binding.eventDate
            val city = binding.eventCity

            subtitle.text = eventItem.subtitle
            date.text = eventItem.date
            city.text = eventItem.city

            image.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    eventItem.imageResID,
                    null
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return eventItems.size
    }
}