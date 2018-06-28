package com.lychee.ui.main.map

import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.extensions.inflate
import kotlinx.android.synthetic.main.view_map_detail.view.*

class MapPagerAdapter constructor(
        private val data : List<Expenditure>
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val root = container.inflate(R.layout.view_map_detail)

        root.rec_map_detail.apply {
            adapter = PictureRecAdapter(null)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        /*
        root.item_scroll_view.apply {
            mScrollListener = listener
            viewPager = container as ViewPager
        }
        */

        container.addView(root)

        return root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = data.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
}