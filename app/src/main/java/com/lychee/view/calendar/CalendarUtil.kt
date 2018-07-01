package com.lychee.view.calendar

import java.util.*

object CalendarUtil {

    fun getMDates(year : Int, month : Int) : List<MDate> {
        val result = mutableListOf<MDate>()

        val today = Calendar.getInstance().seoul()

        Calendar.getInstance().let {
            // YEAR
            it.apply { setYear(year); setDate(1) }

            // LAST MONTH
            val numberOfDatesLastMonth = it.apply { set(Calendar.MONTH, month - 2) }.getActualMaximum(Calendar.DATE)

            // CURRENT MONTH
            val numberOfDates = it.apply { set(Calendar.MONTH, month - 1) }.getActualMaximum(Calendar.DATE)
            val startDate = it.get(Calendar.DAY_OF_WEEK)

            repeat(startDate - 1) {
                val calendar = Calendar.getInstance()
                        .apply { set(year, month - 2, numberOfDatesLastMonth + it - startDate + 2) }

                result.add(
                        MDate(calendar,
                            isSaturday(calendar),
                            isSunday(calendar),
                            false,
                            isToday(today, calendar),
                            null
                        )
                )
            }

            repeat(numberOfDates) {
                val calendar = Calendar.getInstance()
                        .apply { set(year, month - 1, it + 1) }

                result.add(
                        MDate(calendar,
                            isSaturday(calendar),
                            isSunday(calendar),
                            true,
                            isToday(today, calendar),
                            null
                        )
                )
            }

            repeat(42 - (startDate - 1 + numberOfDates)) {
                val calendar = Calendar.getInstance()
                        .apply { set(year, month, it + 1) }

                result.add(
                        MDate(calendar,
                                isSaturday(calendar),
                                isSunday(calendar),
                                false,
                                isToday(today, calendar),
                                null
                        )
                )
            }
        }

        return result
    }

    private fun isSaturday(calendar : Calendar) : Boolean = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY

    private fun isSunday(calendar : Calendar) : Boolean = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

    private fun isToday(today : Calendar, calendar : Calendar) : Boolean
            = (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            && today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
            && today.get(Calendar.DATE) == calendar.get(Calendar.DATE))
}