package com.gkonstantakis.pokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkonstantakis.pokemon.databinding.EventItemBinding
import com.gkonstantakis.pokemon.ui.models.EventAdapterItem


class EventsAdapter() :
    RecyclerView.Adapter<EventsAdapter.EventItemViewHolder>() {

    inner class EventItemViewHolder(val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<EventAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: EventAdapterItem,
            newItem: EventAdapterItem
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: EventAdapterItem,
            newItem: EventAdapterItem
        ): Boolean {
            return true
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

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
        val eventItem = differ.currentList[position]
        holder.binding.apply {
            val image = this.eventImage
            val subtitle = this.eventSubtitle
            val date = this.eventDate
            val city = this.eventCity

            subtitle.text = eventItem.subtitle
            date.text = eventItem.date
            city.text = eventItem.city

            image.background = root.context.getDrawable(eventItem.imageResID)
        }
    }

    override fun getItemCount() = differ.currentList.size
}