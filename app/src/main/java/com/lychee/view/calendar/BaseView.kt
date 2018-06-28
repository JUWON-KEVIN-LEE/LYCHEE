package com.lychee.view.calendar

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lychee.R
import com.lychee.extensions.dpToPx

abstract class BaseView : View, View.OnClickListener {

    val mCurrentMonthTextPaint = Paint()

    val mCurrentMonthSaturdayTextPaint = Paint()

    val mCurrentMonthHolidayTextPaint = Paint()

    val mOtherMonthTextPaint = Paint()

    val mOtherMonthSaturdayTextPaint = Paint()

    val mOtherMonthHolidayTextPaint = Paint()

    val mSelectedPaint = Paint()

    val mTodayPaint = Paint()

    val mTodayCirclePaint = Paint()

    // CALENDAR
    val mDelegate : CalendarDelegate = CalendarDelegate()

    lateinit var mDates : List<MDate>

    // CURRENT DATE POSITION
    var mPosition : Int = -1

    var mDateHeight : Float = 0f

    var mDateWidth : Float = 0f

    val mTextHeight : Float

    val mPadding : Float

    // TOUCH EVENT
    var mX : Float = 0f

    var mY : Float = 0f

    var isClicked : Boolean = true

    constructor(context: Context) : this(context, null) // UNUSED

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initPaint(context)

        mTextHeight = mCurrentMonthTextPaint.fontMetrics.let{ -(it.descent + it.ascent) }
        mPadding = context.dpToPx(4f)
    }

    private fun initPaint(context : Context) {
        val sp = context.dpToPx(14.0f)

        mCurrentMonthTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.currentMonthColor)
            isFakeBoldText = true
            textSize = sp
        }

        mCurrentMonthSaturdayTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.currentMonthSaturdayColor)
            isFakeBoldText = true
            textSize = context.dpToPx(14.0f)
        }

        mCurrentMonthHolidayTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.currentMonthHolidayColor)
            isFakeBoldText = true
            textSize = sp
        }

        mOtherMonthTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.otherMonthColor)
            isFakeBoldText = true
            textSize = sp
        }

        mOtherMonthSaturdayTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.otherMonthSaturdayColor)
            isFakeBoldText = true
            textSize = sp
        }

        mOtherMonthHolidayTextPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = ContextCompat.getColor(context, R.color.otherMonthHolidayColor)
            isFakeBoldText = true
            textSize = sp
        }

        mSelectedPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }

        mTodayPaint.apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.White)
            isFakeBoldText = true
            textSize = sp
        }

        mTodayCirclePaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.pointerCount > 1) return false

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.x
                mY = event.y
                isClicked = true
            }
            MotionEvent.ACTION_MOVE -> {
                if(isClicked) {
                    val dy = event.y - mY
                    isClicked = Math.abs(dy) <= 50
                }
            }
            MotionEvent.ACTION_UP -> {
                mX = event.x
                mY = event.y
            }
        }

        return super.onTouchEvent(event)
    }

    fun isSelected(mDate : MDate): Boolean = (mDates.indexOf(mDate) == mPosition)
}