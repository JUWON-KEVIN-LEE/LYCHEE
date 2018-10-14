package com.lychee.ui.home

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.databinding.ItemRecyclerViewCommonBinding
import com.lychee.ui.home.HomeRecyclerViewHolder.HomeEmptyViewHolder
import com.lychee.ui.home.HomeRecyclerViewHolder.HomeRecentViewHolder

class HomeRecyclerViewAdapter constructor(
        private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var expenses = emptyList<Expense>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when(expenses.size) {
            0 -> R.layout.item_recycler_view_empty_home
            else -> R.layout.item_recycler_view_common
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            R.layout.item_recycler_view_empty_home -> HomeEmptyViewHolder(
                    layoutInflater.inflate(viewType, parent, false)
            )
            R.layout.item_recycler_view_common -> HomeRecentViewHolder(
                    ItemRecyclerViewCommonBinding.inflate(layoutInflater, parent, false)
            )
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        val size = expenses.size

        return when {
            size > 3 -> 3
            size > 0 -> size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HomeRecentViewHolder -> holder.binding.apply {
                expense = expenses[position]
                setLifecycleOwner(lifecycleOwner)
                executePendingBindings()
            }
            is HomeEmptyViewHolder -> Unit
        }
    }

    companion object {
        private const val EMPTY_DATA_VIEW_TYPE = 0
        private const val VALID_DATA_VIEW_TYPE = 1
    }
}