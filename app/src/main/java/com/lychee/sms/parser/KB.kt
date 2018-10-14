package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
신용
[Web발신]
KB국민카드 9*1* 승인
구*모
75,200원 일시불
08/19 16:29
한국철도공사
누적 ***원

체크
[Web발신]
KB국민체크(7*8*)
금*문
08/14 19:00
7,290원
지에스25서초진 사용
 */
object KB: Parser() {

    override fun smsToExpense(origin: String): Expense? {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split("\n")

        val type = checkCardType(words)

        return when(type) {
            is CardType.DebitCard -> tryOrNull { smsToExpenseDebitCard(words) }
            is CardType.CreditCard -> tryOrNull { smsToExpenseCreditCard(words) }
            is CardType.Unknown -> null
        }
    }

    override fun checkCardType(words: List<String>): CardType {
        return super.checkCardType(words)
    }

    /**
     * 신용카드
     */
    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        val user = words[1]

        val b = words[0].split(" ")

        val cardNumber = b[1]

        val approved = b[2].contains("승인")

        val c = words[2].split(" ")

        val charge = c[0]
                .substring(0, c[0].length - 1)
                .replace(",", "")
                .toInt()

        val installment = c[1] == "일시불"

        val d = words[3].split(" ")

        val date = d[0].split("/")

        val time = d[1].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val shop = words[4]

        /* 총 금액 */
        val accumulation = words[5]
                .substring(2, words[5].length - 1)
                .replace(",", "")
                .toInt()

        return Expense(
                card = Card(bank = "KB국민카드", cardNumber = cardNumber),
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0),
                approved = approved)
    }

    /**
     * 체크카드
     */
    override fun smsToExpenseDebitCard(words: List<String>): Expense {

        val user = words[1]

        val cardNumber = words[0]
                .split("(")[1]
                .let { it.substring(0, it.length - 1) }

        val charge = words[3]
                .substring(0, words[3].length - 1)
                .replace(",", "")
                .toInt()

        val d = words[2].split(" ")

        val date = d[0].split("/")

        val time = d[1].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val shop = words[4].split(" ")[0]

        return Expense(
                card = Card(bank = "KB국민카드", cardNumber = cardNumber),
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0))
    }
}