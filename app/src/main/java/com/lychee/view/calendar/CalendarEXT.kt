package com.lychee.view.calendar

import java.util.*

fun Calendar.setYear(year : Int) = this.set(Calendar.YEAR, year)

fun Calendar.setMonth(month : Int) = this.set(Calendar.MONTH, month)

fun Calendar.seoul() : Calendar = this.apply { timeZone = TimeZone.getTimeZone("Asia/Seoul") }

/**
 * 월의 마지막 날
 */
fun Calendar.getNumberOfDays(year : Int, month : Int) : Int
        = this.apply { set(Calendar.YEAR, year); set(Calendar.MONTH, month) }.getActualMaximum(Calendar.DATE)

/**
 *  월의 시작 요일
 */
fun Calendar.getFirstDayOfWeek(year : Int, month : Int) : Int
        = this.apply { set(year, month, 1) }.get(Calendar.DAY_OF_WEEK)
