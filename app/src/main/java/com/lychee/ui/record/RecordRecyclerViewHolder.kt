package com.lychee.ui.record

import android.support.v7.widget.RecyclerView
import com.lychee.BR
import com.lychee.data.core.model.Expense
import com.lychee.databinding.ItemRecyclerViewCommonBinding

class RecordRecyclerViewHolder(
        private val binding : ItemRecyclerViewCommonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(expense: Expense){
        with(binding){
            setVariable(BR.expense, expense)
            executePendingBindings()
        }
    }
}