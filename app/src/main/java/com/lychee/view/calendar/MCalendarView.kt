package com.lychee.view.calendar

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.lychee.R
import com.lychee.extensions.inflate
import com.lychee.extensions.invisible
import com.lychee.extensions.update
import com.lychee.extensions.visible
import com.lychee.view.calendar.MCalendarView.Direction.*
import kotlinx.android.synthetic.main.view_calendar.view.*

class MCalendarView : ConstraintLayout {

    private val guidelineTop : Guideline

    private val guidelineBottom : Guideline

    private val guidelineWeekBottom : Guideline

    private val monthViewPager : MonthViewPager

    private val weekViewPager : WeekViewPager

    private var mLastX : Float = 0f

    private var mLastY : Float = 0f

    private var mActivePointerId : Int = INVALID_POINTER_ID

    private var mDirection : Direction = None()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // ATTACH
        this.inflate(R.layout.view_calendar, true)
        id = View.generateViewId()

        // GUIDELINE
        guidelineTop = guideline_calendar_top
        guidelineBottom = guideline_calendar_bottom

        guidelineWeekBottom = guideline_calendar_week_bottom

        // VIEW PAGER
        monthViewPager = month_view_pager
        weekViewPager = week_view_pager

        init()
    }

    private fun init() {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y

                mActivePointerId = event.getPointerId(0)

                test_week_wrapper.visible()
                test_month_wrapper.visible()
            }
            MotionEvent.ACTION_MOVE -> {
                val activeIndex = event.findPointerIndex(mActivePointerId)

                if(activeIndex == -1) return true

                val x = event.getX(activeIndex)
                val y = event.getY(activeIndex)

                val dX = x - mLastX
                val dY = y - mLastY

                mDirection = direction(dX, dY)

                when(mDirection) {
                    is Up, is Down -> {
                        val delta = dY / height
                        onUpdate(delta)
                    }
                    is Left, is Right -> {
                        // DO VIEW PAGER THING
                    }
                }

                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                onCompleteUpdate()

                mActivePointerId = INVALID_POINTER_ID
            }
        }

        return true
    }

    private fun onUpdate(delta : Float) {
        Log.d("JUWONLEE", "onUpdate(delta : $delta)")

        val params = (guidelineBottom.layoutParams as ConstraintLayout.LayoutParams)

        val min = (guidelineWeekBottom.layoutParams as ConstraintLayout.LayoutParams).guidePercent
        val max = 1.0f

        val newGuidePercent = params.guidePercent + delta

        when {
            newGuidePercent < min -> params.guidePercent = min
            newGuidePercent > max -> params.guidePercent = max
            else -> params.guidePercent  = newGuidePercent
        }

        guidelineBottom.layoutParams = params
    }

    private fun onCompleteUpdate() = when(mDirection) {
        // SHRINK
        is Up -> {
            val min = (guidelineWeekBottom.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            onScale(min)
        }
        // EXPAND
        is Down -> { onScale(1.0f) }
        else -> { /* NOTHING */ }
    }


    private fun onScale(ratio : Float) = update {
        setGuidelinePercent(R.id.guideline_calendar_bottom, ratio)

        TransitionManager.beginDelayedTransition(this@MCalendarView, ChangeBounds().apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500

            addListener(object  : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    if(ratio != 1.0f) {
                        test_week_wrapper.visible()
                        test_month_wrapper.invisible()
                    }
                }
                override fun onTransitionResume(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionStart(transition: Transition) {}
            })
        })
    }

    private fun direction(dX : Float, dY : Float) : Direction {
        return if(Math.abs(dX) > Math.abs(dY)) { // IF HORIZONTAL
            if(dX > 0) Right() else Left()
        }
        else { // ELSE VERTICAL
            if(dY > 0) Down() else Up()
        }
    }

    private sealed class Direction { // DRAG 기준
        class Down : Direction() // y - mLastY > 0
        class Up : Direction() // y - mLastY < 0
        class Right : Direction()
        class Left : Direction()
        class None : Direction()
    }

}