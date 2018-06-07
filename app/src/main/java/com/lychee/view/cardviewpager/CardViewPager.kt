package com.lychee.view.cardviewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Measure > Layout
 */
class CardViewPager : ViewPager {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    /**
     * measureChild() > measureChildWithMargins() > measureChildren()
     * then CALL
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun onMeasurePage(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }


}