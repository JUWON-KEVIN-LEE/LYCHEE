package com.lychee.view.map

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class MapItemScrollView : ScrollView {

    var isExpanded : Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        // IF ON EXPAND OR SHRINK CALLED
        // THEN SCROLL TO TOP
        smoothScrollTo(0, 0)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // BLOCK
        // IF NOT > FOCUSED VIEW = RECYCLER VIEW > FORCE TO SCROLL Y TO REC VIEW
        // super.onSizeChanged(w, h, oldw, oldh)
    }

    // 확장된 상태에서만 touch event 에 반응하도록 설정
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean
            = if(isExpanded) super.onInterceptTouchEvent(ev) else false

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if(isExpanded) super.onTouchEvent(ev)
                else isExpanded
            }
            else -> super.onTouchEvent(ev)
        }
    }
}
