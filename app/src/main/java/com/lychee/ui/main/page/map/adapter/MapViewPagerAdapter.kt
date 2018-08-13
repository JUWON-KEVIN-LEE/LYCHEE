package com.lychee.ui.main.page.map.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R

class MapViewPagerAdapter constructor(
        private val context: Context
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view_pager_map, container, false)

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = 5 // TEST TODO

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
}