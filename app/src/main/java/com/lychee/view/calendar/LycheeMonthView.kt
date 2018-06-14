package com.lychee.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout

class LycheeMonthView : LinearLayout {

    // var gestureListener

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // vertical
        orientation = LinearLayout.VERTICAL

        // initiate
        init()
    }

    private fun init() {
        val days = CalendarUtil.getDays()

        for (i in 0 until 6) {
            val weekView = LycheeWeekView(context)

            repeat(7, { val index = i * 7 + it; weekView.addDay(days[index]) })

            addView(weekView, LinearLayout.LayoutParams(MATCH_PARENT, 0, 1f))
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                val dayWidth = width / 7
                val dayHeight = height / 6

                val dayIndex = (event.x / dayWidth).toInt() // 요일 단위
                val weekIndex = (event.y / dayHeight).toInt() // 주 단위
            }
            MotionEvent.ACTION_MOVE -> {

            }
        }

        return super.onTouchEvent(event)
    }

}
