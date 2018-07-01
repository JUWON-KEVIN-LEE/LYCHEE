package com.lychee.view.calendar

import java.util.*

/**
 * CALENDAR
 */
fun Calendar.setYear(year : Int) = set(Calendar.YEAR, year)

fun Calendar.setMonth(month : Int) = set(Calendar.MONTH, month)

fun Calendar.setDate(date : Int) = set(Calendar.DATE, date)

fun Calendar.seoul() : Calendar = apply { timeZone = TimeZone.getTimeZone("Asia/Seoul") }