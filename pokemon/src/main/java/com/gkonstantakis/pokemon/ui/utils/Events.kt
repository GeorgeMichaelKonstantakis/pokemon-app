package com.gkonstantakis.pokemon.ui.utils

import android.content.Context
import com.gkonstantakis.pokemon.R
import com.gkonstantakis.pokemon.ui.models.EventAdapterItem

object Events {

    fun getEventsList(context: Context): List<EventAdapterItem> {
        var eventList = ArrayList<EventAdapterItem>()

        eventList.add(
            EventAdapterItem(
                imageResID = R.drawable.event_one,
                subtitle = context.resources.getString(R.string.event_one_subtitle_txt),
                date = context.resources.getString(R.string.event_one_date_txt),
                city = context.resources.getString(R.string.event_one_city_txt),
            )
        )

        eventList.add(
            EventAdapterItem(
                imageResID = R.drawable.event_two,
                subtitle = context.resources.getString(R.string.event_two_subtitle_txt),
                date = context.resources.getString(R.string.event_two_date_txt),
                city = context.resources.getString(R.string.event_two_city_txt),
            )
        )

        eventList.add(
            EventAdapterItem(
                imageResID = R.drawable.main_event,
                subtitle = context.resources.getString(R.string.main_event_name_text),
                date = context.resources.getString(R.string.main_event_date_text),
                city = context.resources.getString(R.string.main_event_city_text),
            )
        )
        return eventList
    }
}