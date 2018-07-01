package com.lychee.view.calendar

import java.util.*

class CalendarDelegate {

    val MIN_YEAR : Int = 1990

    val MAX_YEAR : Int = 2030

    var mSelectedYear : Int = 0

    var mSelectedMonth : Int = 0

    var mSelectedDay : Int = 0

    val mToday : Calendar = Calendar.getInstance().seoul()

    var mDateHeight :Float = 0f

    init {
        mToday.let {
            mSelectedYear = it.get(Calendar.YEAR)
            mSelectedMonth = it.get(Calendar.MONTH)
            mSelectedDay = it.get(Calendar.DATE)
        }
    }



    fun onDateSelect(year : Int, month : Int, day : Int) {
        mSelectedYear = year
        mSelectedMonth = month
        mSelectedDay = day

        // TODO interface
    }

    interface OnDateSelectListener {
        fun onSelect(year : Int, month : Int, day : Int)
    }
}