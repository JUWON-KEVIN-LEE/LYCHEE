package com.lychee

import org.junit.Assert.assertEquals
import org.junit.Test

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
    fun parse() {
        val testBody = "[Web발신]\n[신한체크승인] 이*원(5780) 08/10 20:53 (금액)53,000원 후암연어식당"

        val words = mutableListOf<String>()

        for(line in testBody.split("\n")) {
            words.addAll(line.split(" "))
        }

        removeHeader(words)

        for(word in words) {
            System.out.println(word)
        }
    }

    // [Web발신]
    fun removeHeader(words: MutableList<String>) = words.remove("[Web발신]")
}
