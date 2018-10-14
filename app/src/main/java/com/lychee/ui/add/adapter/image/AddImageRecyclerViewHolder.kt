package com.lychee.ui.add.adapter.image

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lychee.databinding.ItemRecyclerViewImageAddBinding
import com.lychee.util.extensions.dpToPx

class AddImageRecyclerViewHolder(
        private val mBinding: ItemRecyclerViewImageAddBinding,
        private val context: Context
): RecyclerView.ViewHolder(mBinding.root) {
    val radius = context.dpToPx(4)

    fun bind(uri: String) {
        Glide.with(context)
                .load("file:$uri")
                .apply(
                        RequestOptions()
                                .transform(MultiTransformation(
                                        CenterCrop(),
                                        RoundedCorners(radius))
                                )
                )
                .into(mBinding.addItemImageView)
    }
}