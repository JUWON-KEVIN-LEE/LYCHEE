package com.lychee.ui.map

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.ItemRecyclerViewDetailMapBinding

class MapDetailPhotoRecyclerViewAdapter: RecyclerView.Adapter<MapDetailPhotoRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapDetailPhotoRecyclerViewHolder {
        val binding = DataBindingUtil.inflate<ItemRecyclerViewDetailMapBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_recycler_view_detail_map,
                parent,
                false
                )

        return MapDetailPhotoRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: MapDetailPhotoRecyclerViewHolder, position: Int) {
        // holder.bind(uri)
    }
}