package com.lychee.sms

import com.lychee.data.model.core.Expenditure

class SmsParser { // class ? object ?

    fun parse(body: String): Expenditure {
        val words = splitInternally(body)

        return Expenditure()
    }

    private fun splitInternally(body: String): List<String> {
        val words = mutableListOf<String>()

        val lines = body.split("\n")
        for(line in lines) {
            words.addAll(
                    line.split(" ")
            )
        }

        removeHeader(words)

        return words
    }

    private fun removeHeader(words: MutableList<String>)
            = words.remove("[Web발신]")
}