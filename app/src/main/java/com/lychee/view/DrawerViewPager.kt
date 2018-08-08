package com.lychee.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.lychee.util.extensions.deviceWidth

class DrawerViewPager: ViewPager {

    private var mX: Float = 0f

    private var isScaling = false

    var onSwipeOutListener: OnSwipeOutListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(currentItem == 0) {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mX = event.rawX
                    isScaling = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = event.rawX - mX
                    if(dX > 50) {
                        onSwipeOutListener?.onSwipe((dX) / context.deviceWidth())
                        isScaling = true
                        mX = event.rawX
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if(isScaling) { onSwipeOutListener?.endSwipe() }
                    mX = 0f
                }
            }
        }

        return super.onTouchEvent(event)
    }

    interface OnSwipeOutListener {
        fun onSwipe(progress: Float)

        fun endSwipe()
    }
}