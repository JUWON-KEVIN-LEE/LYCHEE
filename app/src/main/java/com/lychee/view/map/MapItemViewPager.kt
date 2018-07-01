package com.lychee.view.map

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import com.lychee.extensions.dpToPx

class MapItemViewPager : ViewPager, OnBoundTransition {

    var mOldHeight : Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        pageMargin = context.dpToPx(8)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if(mOldHeight != 0) {
            if(height > mOldHeight) onExpand()
            else onShrink()
        }

        mOldHeight = height
    }

    override fun onExpand() {
        for(i in 0 until childCount)
            (getChildAt(i) as? MapItemScrollView)?.isExpanded = true
    }

    override fun onShrink() {
        for(i in 0 until childCount)
            (getChildAt(i) as? MapItemScrollView)?.isExpanded = false
    }
}