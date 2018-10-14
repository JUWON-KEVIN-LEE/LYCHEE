package com.lychee.ui.add.adapter.place

import android.support.v7.widget.RecyclerView
import com.lychee.data.core.model.Place
import com.lychee.databinding.ItemRecyclerViewPlaceAddBinding

class AddPlaceRecyclerViewHolder(
        private val mBinding: ItemRecyclerViewPlaceAddBinding
): RecyclerView.ViewHolder(mBinding.root) {

    fun bind(place: Place) {
        mBinding.place = place
        mBinding.executePendingBindings()
    }
}