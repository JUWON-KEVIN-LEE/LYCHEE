package com.lychee.ui.add.adapter.place

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.data.core.model.Place
import com.lychee.databinding.ItemRecyclerViewPlaceAddBinding

class AddPlaceRecyclerViewAdapter(
        private val context: Context
): RecyclerView.Adapter<AddPlaceRecyclerViewHolder>() {

    var places = mutableListOf<Place>()
        set(value) {
            field.clear()
            places.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPlaceRecyclerViewHolder {
        val binding =
                ItemRecyclerViewPlaceAddBinding.inflate(LayoutInflater.from(context), parent, false)

        return AddPlaceRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: AddPlaceRecyclerViewHolder, position: Int) {
        holder.bind(places[position])
    }
}