package com.lychee.ui.main.record

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lychee.data.model.Expenditure

class RecordRecAdapter constructor(
        private val data : List<Expenditure>
): RecyclerView.Adapter<RecordRecHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordRecHolder
     = RecordRecHolder(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecordRecHolder, position: Int)
        = holder.bind(data[position])

}