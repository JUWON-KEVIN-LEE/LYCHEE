package com.lychee.ui.main.map

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lychee.R
import com.lychee.extensions.inflate

class PictureRecAdapter constructor(
        private val urls : List<String>?
): RecyclerView.Adapter<PictureRecHolder>() {

    override fun getItemViewType(position: Int): Int
        = if(urls == null || urls.isEmpty()) DATA_EMPTY_TYPE else DATA_EXIST_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureRecHolder
        = if(viewType == DATA_EMPTY_TYPE) { EmptyHolder(parent.inflate(R.layout.rec_item_map_detail)) } // TODO EMPTY LAYOUT
          else { PictureHolder(parent.inflate(R.layout.rec_item_map_detail)) }

                                                                        // TEST TODO 4 > 1
    override fun getItemCount(): Int = if(urls == null || urls.isEmpty()) 4 else urls.size

    override fun onBindViewHolder(holder: PictureRecHolder, position: Int) {
        when(holder) {
            is EmptyHolder -> { holder.onBindEmpty() }
            is PictureHolder -> { urls?.let { holder.onBind(it[position]) } }
        }
    }

    companion object {
        const val DATA_EMPTY_TYPE = 0
        const val DATA_EXIST_TYPE = 1
    }
}