package com.lychee.ui.main.page.home.adapter

import android.support.v7.widget.RecyclerView
import com.lychee.BR
import com.lychee.data.core.model.Expenditure
import com.lychee.databinding.ItemRecyclerViewCommonBinding

class HomeRecentRecyclerViewHolder(
        private val binding: ItemRecyclerViewCommonBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(expenditure: Expenditure) {
        with(binding) {
            setVariable(BR.expenditure, expenditure)
            executePendingBindings()
        }
    }
}