package com.lychee.ui.main.page.map.adapter

import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.ItemViewPagerEmptyMapBinding
import com.lychee.databinding.ItemViewPagerMapBinding

class MapViewPagerAdapter constructor(
        private val detailShowTask: () -> Unit
): PagerAdapter() {

    var expenditures: MutableList<Expenditure> = mutableListOf()
        set(value) {
            field.clear()
            expenditures.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    override fun getCount(): Int = if(expenditures.size == 0) 1 else expenditures.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if(expenditures.size == 0) {
            val binding = DataBindingUtil.inflate<ItemViewPagerEmptyMapBinding>(
                    LayoutInflater.from(container.context),
                    R.layout.item_view_pager_empty_map,
                    container,
                    false)

            container.addView(binding.root)

            return binding.root
        } else {
            val binding = DataBindingUtil.inflate<ItemViewPagerMapBinding>(
                    LayoutInflater.from(container.context),
                    R.layout.item_view_pager_map,
                    container,
                    false)

            binding.root.setOnClickListener { detailShowTask() }

            container.addView(binding.root)

            return binding.root
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
}