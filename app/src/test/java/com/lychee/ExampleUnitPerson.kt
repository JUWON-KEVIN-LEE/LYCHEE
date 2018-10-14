package com.lychee

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitPerson {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun calendar() {
        val date = "10/19"
        val time = "18:28"

        val d = date.split("/")

        val month = d[0].toInt()
        val day = d[1].toInt()

        val t = time.split(":")

        val hour = t[0].toInt()
        val minute = t[1].toInt()

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, day)

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        print("월일시분: ${calendar.get(Calendar.MONTH) + 1} " +
                "/ ${calendar.get(Calendar.DATE)} " +
                "/ ${calendar.get(Calendar.HOUR_OF_DAY)} " +
                "/ ${calendar.get(Calendar.MINUTE)}")
    }

    @Test
    fun smsToExpense() {
        val origin = "[Web발신]\n" +
                "삼성6125승인\n" +
                "202,000원 일시불\n" +
                "10/19 12:28 ㈜일현전자\n"

        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split("\n")

        val approved = words[0].endsWith("승인")

        if(approved) {
            val cardNumber = words[0].substring(2, 6)

            val c = words[1].split(" ")

            val charge = c[0]
                    .substring(0, c[0].length - 1)
                    .replace(",", "")
                    .toInt()

            val installment = c[1] == "일시불"

            val d = words[2].split(" ")

            val date = d[0]

            val time = d[1]

            val shop = d[2]

            print("$cardNumber $shop $charge $date $time ${if(installment) "일시불" else "할부"}")
        }

    }
}