package com.lychee.ui.main.page.record.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.RecordListLayoutBinding

/**
 * Created by user on 2018-08-09.
 */
class RecordRecyclerViewAdapter(
        private val expenditures: List<Expenditure>
) : RecyclerView.Adapter<RecordRecyclerViewHolder>() {

    override fun getItemCount(): Int = expenditures.size

    override fun onBindViewHolder(holder: RecordRecyclerViewHolder, position: Int) = holder.bind(expenditures[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordRecyclerViewHolder {
        val itemBinding = DataBindingUtil.inflate<RecordListLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.record_list_layout,
                parent,
                false
        )

        return RecordRecyclerViewHolder(itemBinding)
    }
}