package com.lychee.ui.main.page.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.core.model.Expenditure
import com.lychee.databinding.ItemRecyclerViewCommonBinding
import com.lychee.databinding.ItemRecyclerViewEmptyHomeBinding

class HomeRecentRecyclerViewAdapter constructor(
        private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var expenditures = mutableListOf<Expenditure>()
        set(value) {
            field.clear()
            expenditures.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if(expenditures.size == 0) EMPTY_DATA_VIEW_TYPE
        else VALID_DATA_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == EMPTY_DATA_VIEW_TYPE) {
            val binding = DataBindingUtil.inflate<ItemRecyclerViewEmptyHomeBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_recycler_view_empty_home,
                    parent,
                    false)

            return HomeEmptyRecyclerViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<ItemRecyclerViewCommonBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_recycler_view_common,
                    parent,
                    false)

            return HomeRecentRecyclerViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return when {
            expenditures.size > 3 -> 3
            expenditures.size in 1..3 -> expenditures.size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(expenditures.size != 0) {
            (holder as? HomeRecentRecyclerViewHolder)?.bind(expenditures[position])
        }
    }

    companion object {
        private const val EMPTY_DATA_VIEW_TYPE = 0
        private const val VALID_DATA_VIEW_TYPE = 1
    }
}