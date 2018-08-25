package com.lychee.view.main

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 네이버 지도 사용 시 ViewPager 좌우 스크롤 시 페이징이 일어남.
 * 이 때문에 좋은 UX 를 주지 못함. Map 페이지에 해당할 때만 Scroll 을 막음.
 */
class LockViewPager: ViewPager {

    var lock: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(lock) return false
        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(lock) return false
        return super.onInterceptTouchEvent(ev)
    }

    override fun scrollTo(x: Int, y: Int) {
        if(!lock) {
            super.scrollTo(x, y)
        }
    }
}