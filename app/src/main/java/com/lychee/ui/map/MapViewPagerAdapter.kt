package com.lychee.ui.map

import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.BR
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.databinding.ItemViewPagerEmptyMapBinding
import com.lychee.databinding.ItemViewPagerMapBinding

class MapViewPagerAdapter constructor(
        private val detailShowTask: () -> Unit
): PagerAdapter() {

    var expenses: MutableList<Expense> = mutableListOf()
        set(value) {
            field.clear()
            expenses.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    override fun getCount(): Int = if(expenses.size == 0) 1 else expenses.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if(expenses.size == 0) {
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

            // Data binding
            with(binding) {
                setVariable(BR.expense, expenses[position])
                executePendingBindings()
                mapItemIndexTextView.text = "${position + 1} / ${expenses.size}"
            }

            // when click show expenditure details
            binding.root.setOnClickListener { detailShowTask() }

            container.addView(binding.root)

            return binding.root
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
}