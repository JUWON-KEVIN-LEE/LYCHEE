package com.lychee.view.calendar

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class MonthViewPager : ViewPager {

    private lateinit var mCalendarDelegate : CalendarDelegate

    private var mCount : Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setup(calendarDelegate : CalendarDelegate) {
        mCalendarDelegate = calendarDelegate
    }

    private fun init() {
        mCount = mCalendarDelegate.run { 12 * (MAX_YEAR - MIN_YEAR + 1) }

        adapter = MonthViewPagerAdapter()
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageSelected(position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        mCalendarDelegate.mDateHeight = height / 6f
    }

    override fun onDetachedFromWindow() {
        adapter = null // INNER CLASS
        super.onDetachedFromWindow()
    }

    inner class MonthViewPagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

        override fun getCount(): Int = mCount

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
                = container.removeView(`object` as View)
    }
}