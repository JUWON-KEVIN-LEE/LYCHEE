package com.lychee.view.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lychee.data.model.Expenditure
import com.lychee.view.calendar.CalendarUtil.getMDates
import java.util.*

class MonthView : BaseView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun set(year : Int, month : Int) {
        mDates = getMDates(year, month)
    }

    // SIZE MEASURING 순서
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // SIZE
        mDateWidth = width / 7f
        mDateHeight = height / 6f
    }

    override fun onDraw(canvas: Canvas) {
        for(i in 0 until 6) {
            for(j in 0 until 7) {
                val index = 7 * i + j
                val mDate = mDates[index]

                val x = mDateWidth * j
                val y = mDateHeight * i

                draw(canvas, mDate, x, y, index)
            }
        }
    }

    private fun draw(canvas : Canvas, mDate: MDate, x : Float, y : Float, index : Int) {
        // TEXT
        if(isSelected(mDate)) onDrawSelected(canvas, x, y) // SELECTED

        onDrawText(canvas, mDate, x, y) // /W TODAY

        // DATA
        mDate.data
                ?.let {

                }
    }

    private fun getIndex() : Int {
        val xIndex = (mX / mDateWidth).toInt()
        val yIndex = (mY / mDateHeight).toInt()

        return 7 * yIndex + xIndex
    }

    override fun onClick(view: View) {
        if(isClicked) {
            val index = getIndex()

            mDates.takeIf { index < 42 }
                    ?.let {
                        val mDate = it[index]

                        if(!mDate.isCurrentMonth) {
                            // MOVE VIEW PAGER
                        }
                    }
        }
    }

    // 선택되었을 경우
    private fun onDrawSelected(canvas : Canvas, x : Float, y : Float) = canvas.drawRect(
            x + mPadding,
            y + mPadding,
            x + mDateWidth - mPadding,
            y + mDateHeight - mPadding,
            mSelectedPaint
    )

    // 지출 내역
    fun onDrawData(canvas : Canvas, data : List<Expenditure>, x : Int, y : Int, index : Int) {

    }

    // 날짜
    private fun onDrawText(canvas : Canvas, mDate : MDate, x : Float, y : Float) {
        val cx = x + mDateWidth / 2
        val cy = y + mDateHeight / 6

        val paint : Paint

        if(mDate.isToday) {
            paint = mTodayPaint

            canvas.drawCircle(cx, cy - mTextHeight / 2, mTextHeight + mPadding / 4, mTodayCirclePaint)
        } else {
            paint =
                    if(mDate.isCurrentMonth) {
                        when {
                            mDate.isHoliday -> mCurrentMonthHolidayTextPaint
                            mDate.isSaturday -> mCurrentMonthSaturdayTextPaint
                            else -> mCurrentMonthTextPaint
                        }
                    } else {
                        when {
                            mDate.isHoliday -> mOtherMonthHolidayTextPaint
                            mDate.isSaturday -> mOtherMonthSaturdayTextPaint
                            else -> mOtherMonthTextPaint
                        }
                    }
        }

        canvas.drawText(mDate.calendar.get(Calendar.DATE).toString(), cx, cy, paint)
    }
}