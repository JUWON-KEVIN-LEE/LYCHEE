package com.lychee.view.calendar

import com.lychee.view.calendar.model.LycheeDay
import com.lychee.view.calendar.model.MDate
import java.util.*

object CalendarUtil {

    /**
     *
     */
    fun getMDates() : List<MDate> {
        return mutableListOf()
    }

    fun getDays() : List<LycheeDay> {
        createCalendar(seoul())
                .let {
                    val year = it.get(Calendar.YEAR)
                    val month = it.get(Calendar.MONTH)
                    val today = it.get(Calendar.DAY_OF_MONTH)

                    // 요청 월의 일수
                    val numberOfDaysInThisMonth = it.getActualMaximum(Calendar.DATE)

                    // 시작 요일
                    val startDay = it.apply { set(Calendar.DATE, 1) }.get(Calendar.DAY_OF_WEEK)

                    // 이전 달
                    val lastDayInPreviousMonth = getNumberOfDays(year, month - 1)
                    val numberOfDaysInPreviousMonth = startDay - 1 // 전달 표시해야 할 날

                    // 다음 달
                    val numberOfDaysInNextMonth = 42 - numberOfDaysInPreviousMonth - numberOfDaysInThisMonth

                    val days = mutableListOf<LycheeDay>()

                    repeat(numberOfDaysInPreviousMonth) { // 31 - 5 + index + 1 > 27 28 29 30 31
                        // TODO PREVIOUS MONTH BOOLEAN
                        index -> days
                            .add(index,
                                    LycheeDay(day = lastDayInPreviousMonth - numberOfDaysInPreviousMonth + index + 1,
                                            today = false,
                                            currentMonth = false)
                            )
                    }

                    repeat(numberOfDaysInThisMonth) {
                        index -> days
                            .add(index + numberOfDaysInPreviousMonth,
                                    LycheeDay(day = index + 1,
                                            today = (index + 1 == today),
                                            currentMonth = true)
                            )
                    }

                    repeat(numberOfDaysInNextMonth) {
                        // TODO NEXT MONTH BOOLEAN
                        index -> days
                            .add(index + numberOfDaysInPreviousMonth + numberOfDaysInThisMonth,
                                    LycheeDay(day = index + 1,
                                            today = false,
                                            currentMonth = false)
                            )
                    }

                    return days
                }
    }

    // 총 42일
    fun getDays(year : Int, month : Int) : List<LycheeDay> {
        if(!checkMonth(month)) throw IllegalArgumentException("ERROR MONTH : $month > 12 or $month < 0")

        createCalendar(seoul())
                .let {
                    // 오늘
                    val thisYear = it.get(Calendar.YEAR)
                    val thisMonth = it.get(Calendar.MONTH)
                    val today = it.get(Calendar.DAY_OF_MONTH)

                    // 요청 월 / 년
                    setMonth(month, it); setYear(year, it)

                    // 요청 월의 일수
                    val numberOfDaysInThisMonth = it.getActualMaximum(Calendar.DATE)

                    // 시작 요일
                    val startDay = it.apply { set(Calendar.DATE, 1) }.get(Calendar.DAY_OF_WEEK)

                    // 이전 달
                    val lastDayInPreviousMonth = getNumberOfDays(year, month - 1)
                    val numberOfDaysInPreviousMonth = startDay - 1 // 전달 표시해야 할 날

                    // 다음 달
                    val numberOfDaysInNextMonth = 42 - numberOfDaysInPreviousMonth - numberOfDaysInThisMonth

                    val days = mutableListOf<LycheeDay>()

                    repeat(numberOfDaysInPreviousMonth) { // 31 - 5 + index + 1 > 27 28 29 30 31
                        // TODO PREVIOUS MONTH BOOLEAN
                        index -> days
                            .add(index,
                                    LycheeDay(day = lastDayInPreviousMonth - numberOfDaysInPreviousMonth + index + 1,
                                        today = false,
                                        currentMonth = false)
                            )
                    }

                    repeat(numberOfDaysInThisMonth) {
                        index -> days
                            .add(index + numberOfDaysInPreviousMonth,
                                    LycheeDay(day = index + 1,
                                        today = (index + 1 == today)
                                                    .and(thisMonth == month)
                                                    .and(thisYear == year),
                                        currentMonth = true)
                            )
                    }

                    repeat(numberOfDaysInNextMonth) {
                        // TODO NEXT MONTH BOOLEAN
                        index -> days
                            .add(index + numberOfDaysInPreviousMonth + numberOfDaysInThisMonth,
                                    LycheeDay(day = index + 1,
                                        today = false,
                                        currentMonth = false)
                            )
                    }

                    return days
                }
    }

    private fun checkMonth(month : Int) : Boolean = (month <= 12).or(month > 0)

    private fun seoul() = TimeZone.getTimeZone("Asia/Seoul")

    private fun createCalendar(timeZone: TimeZone) = Calendar.getInstance(timeZone)

    private fun setYear(year : Int, calendar: Calendar) = calendar.set(Calendar.YEAR, year)

    private fun setMonth(month : Int, calendar: Calendar) = calendar.set(Calendar.MONTH, month)

    /**
     *  마지막 날
     */
    fun getNumberOfDays(year : Int, month : Int) : Int
        = Calendar
            .getInstance()
            .apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
            }
            .getActualMaximum(Calendar.DATE)

    /**
     *  월의 시작 요일
     */
    fun getFirstDayOfWeek(year : Int, month : Int) : Int
        = Calendar
            .getInstance()
            .apply { set(year, month, 1) }
            .get(Calendar.DAY_OF_WEEK)

    /**
     *  마지막 날 요일
     */
    fun getLastDayOfWeek(year : Int, month : Int, days : Int) : Int
            = Calendar
            .getInstance()
            .apply { set(year, month, days) }
            .get(Calendar.DAY_OF_WEEK) // 1 = Sun, 2 = Mon
}