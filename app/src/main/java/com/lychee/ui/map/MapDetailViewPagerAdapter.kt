package com.lychee.ui.map

import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.databinding.ItemViewPagerDetailMapBinding

class MapDetailViewPagerAdapter: PagerAdapter() {

    var expenses: MutableList<Expense> = mutableListOf()
        set(value) {
            field.clear()
            expenses.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    override fun getCount(): Int = if(expenses.size == 0) 1 else expenses.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = DataBindingUtil.inflate<ItemViewPagerDetailMapBinding>(
                LayoutInflater.from(container.context),
                R.layout.item_view_pager_detail_map,
                container,
                false
        )

        with(binding.mapDetailPhotoRecyclerView) {
            adapter = MapDetailPhotoRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.HORIZONTAL, false)
        }

        container.addView(binding.root)

        return binding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
}