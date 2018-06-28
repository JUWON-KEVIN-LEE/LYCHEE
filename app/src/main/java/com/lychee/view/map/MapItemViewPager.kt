package com.lychee.view.map

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log

class MapItemViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d("JUWONLEE", "ViewPager onLayout() called")
        super.onLayout(changed, l, t, r, b)
    }

    fun onExpand() { (getChildAt(currentItem) as? MapItemScrollView)?.isExpanded = true }

    fun onShrink() { (getChildAt(currentItem) as? MapItemScrollView)?.isExpanded = false }
}