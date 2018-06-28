package com.lychee.view.calendar

sealed class CalendarMode {
    class FullMonthMode : CalendarMode()
    class MonthMode : CalendarMode()
    class WeekMode : CalendarMode()
}