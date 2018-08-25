package com.lychee.ui.main.page.record.adapter

import android.support.v7.widget.RecyclerView
import com.lychee.BR
import com.lychee.data.model.Expenditure
import com.lychee.databinding.RecordListLayoutBinding

class RecordRecyclerViewHolder(
        private val binding : RecordListLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(expenditure: Expenditure){
        with(binding){
            setVariable(BR.expenditure, expenditure)
            executePendingBindings()
        }
    }
}