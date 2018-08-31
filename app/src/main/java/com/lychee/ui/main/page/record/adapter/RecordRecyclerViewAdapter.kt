package com.lychee.ui.main.page.record.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.core.model.Expenditure
import com.lychee.databinding.ItemRecyclerViewCommonBinding
import com.lychee.databinding.ItemRecyclerViewEmptyHomeBinding

/**
 * Created by user on 2018-08-09.
 */
class RecordRecyclerViewAdapter(
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var expenditures: MutableList<Expenditure> = mutableListOf()
        set(value) {
            field.clear()
            expenditures.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if(expenditures.size == 0) EMPTY
        else NOT_EMPTY
    }

    override fun getItemCount(): Int = if(expenditures.size == 0) 1 else expenditures.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(expenditures.size != 0) {
            (holder as? RecordRecyclerViewHolder)?.bind(expenditures[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == EMPTY) {
            val mBinding = DataBindingUtil.inflate<ItemRecyclerViewEmptyHomeBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_recycler_view_empty_home,
                    parent,
                    false
            )

            return RecordEmptyRecyclerViewHolder(mBinding)
        } else {
            val mBinding = DataBindingUtil.inflate<ItemRecyclerViewCommonBinding>(
                    LayoutInflater.from(context),
                    R.layout.item_recycler_view_common,
                    parent,
                    false)

            return RecordRecyclerViewHolder(mBinding)
        }
    }

    companion object {
        private const val EMPTY = 0
        private const val NOT_EMPTY = 1
    }
}