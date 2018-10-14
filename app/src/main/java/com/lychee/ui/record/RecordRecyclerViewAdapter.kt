package com.lychee.ui.record

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.data.core.model.Expense
import com.lychee.databinding.ItemRecyclerViewCommonBinding
import com.lychee.databinding.ItemRecyclerViewEmptyHomeBinding

/**
 * Created by user on 2018-08-09.
 */
class RecordRecyclerViewAdapter(
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var expenses: MutableList<Expense> = mutableListOf()
        set(value) {
            field.clear()
            expenses.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if(expenses.size == 0) EMPTY
        else NOT_EMPTY
    }

    override fun getItemCount(): Int = if(expenses.size == 0) 1 else expenses.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(expenses.size != 0) {
            (holder as? RecordRecyclerViewHolder)?.bind(expenses[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == EMPTY) {
            val mBinding
                    = ItemRecyclerViewEmptyHomeBinding.inflate(LayoutInflater.from(context), parent, false)

            RecordEmptyRecyclerViewHolder(mBinding)
        } else {
            val mBinding
                    = ItemRecyclerViewCommonBinding.inflate(LayoutInflater.from(context), parent, false)

            RecordRecyclerViewHolder(mBinding)
        }
    }

    companion object {
        private const val EMPTY = 0
        private const val NOT_EMPTY = 1
    }
}