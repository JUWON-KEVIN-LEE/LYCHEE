package com.lychee.view.calendar

import com.lychee.data.model.Expenditure
import java.util.*

data class MDate (
        val calendar : Calendar,
        val isSaturday : Boolean,
        // Sunday or Holiday, week == 1
        val isHoliday : Boolean,
        val isCurrentMonth : Boolean,
        val isToday : Boolean,
        // Expenditure data
        val data : List<Expenditure>?
)