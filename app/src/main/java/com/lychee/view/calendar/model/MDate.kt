package com.lychee.view.calendar.model

import android.support.annotation.IntRange
import com.lychee.data.model.Expenditure

data class MDate (
        // 1 - 31
        @IntRange(from = 1, to = 31) val day : Int,
        // 0 - 11, Calendar.JANUARY - Calendar.DECEMBER
        @IntRange(from = 0, to = 11) val month : Int,
        val year : Int,
        // 1 - 7, Calendar.SUNDAY - Calendar.SATURDAY
        @IntRange(from = 1, to = 7) val week : Int,
        // Sunday or Holiday, Default = (week == 1)
        val isHoliday : Boolean,
        val isCurrentMonth : Boolean,
        // Expenditure data
        val data : List<Expenditure>?
)