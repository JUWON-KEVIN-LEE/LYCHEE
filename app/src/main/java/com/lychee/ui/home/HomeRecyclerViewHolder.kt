package com.lychee.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import com.lychee.databinding.ItemRecyclerViewCommonBinding

sealed class HomeRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    class HomeEmptyViewHolder(itemView: View): HomeRecyclerViewHolder(itemView)

    class HomeRecentViewHolder(
            val binding: ItemRecyclerViewCommonBinding
    ): HomeRecyclerViewHolder(binding.root)
}