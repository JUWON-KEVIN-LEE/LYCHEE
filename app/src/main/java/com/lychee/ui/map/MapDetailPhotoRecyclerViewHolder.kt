package com.lychee.ui.map

import android.net.Uri
import android.support.v7.widget.RecyclerView
import com.lychee.databinding.ItemRecyclerViewDetailMapBinding

class MapDetailPhotoRecyclerViewHolder(
        private val binding: ItemRecyclerViewDetailMapBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: Uri) {
        binding.mapDetailPhotoImageView.setImageURI(uri)
    }
}