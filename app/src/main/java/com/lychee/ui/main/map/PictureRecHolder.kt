package com.lychee.ui.main.map

import android.support.v7.widget.RecyclerView
import android.view.View
import com.lychee.extensions.loadImage
import kotlinx.android.synthetic.main.rec_item_map_detail.view.*

/**
 * TODO REFACTOR
 */
abstract class PictureRecHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindEmpty()

    abstract fun onBind(url : String)
}

class EmptyHolder(itemView : View) : PictureRecHolder(itemView) {

    override fun onBindEmpty() { /* TODO */ }

    override fun onBind(url : String) {/* NOTHING TO DO */}
}

class PictureHolder(itemView : View) : PictureRecHolder(itemView) {

    override fun onBindEmpty() {/* NOTHING TO DO */}

    override fun onBind(url : String) {
        itemView.picture.loadImage(url)
    }
}