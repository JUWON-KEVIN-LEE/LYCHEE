package com.lychee.ui.main.home

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lychee.data.model.Expenditure

class HomeRecAdapter constructor(
        private val data : List<Expenditure>
) : RecyclerView.Adapter<HomeRecHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecHolder
        = HomeRecHolder(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HomeRecHolder, position: Int) = holder.bind(data[position])
}