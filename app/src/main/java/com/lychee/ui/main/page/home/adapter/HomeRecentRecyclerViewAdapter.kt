package com.lychee.ui.main.page.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.ItemRecyclerViewHomeBinding
import com.lychee.mock.MockData

class HomeRecentRecyclerViewAdapter constructor(
        private val context: Context
): RecyclerView.Adapter<HomeRecentRecyclerViewHolder>() {

    private var mExpenditures: List<Expenditure> = MockData.get()

    fun updateData(expenditures: List<Expenditure>) {
        mExpenditures = expenditures
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecentRecyclerViewHolder {
        val binding = DataBindingUtil.inflate<ItemRecyclerViewHomeBinding>(
                LayoutInflater.from(context),
                R.layout.item_recycler_view_home,
                parent,
                false)

        return HomeRecentRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: HomeRecentRecyclerViewHolder, position: Int) {
        holder.bind(mExpenditures[position])

    }
}