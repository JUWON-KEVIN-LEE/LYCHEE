package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
[Web발신]
현대ZERO승인 김*원
68,000원 일시불
08/10 21:03 룸바캉스
1.2% 할인
 */
object Hyundai: Parser() {

    /**
     * LOGIN NOT SAFE
     */
    override fun smsToExpense(origin: String): Expense? {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split("\n")

        val type = checkCardType(words)

        return when(type) {
            /* REPORT NOT SUPPORTED SMS & EXCEPTION */
            is CardType.DebitCard -> tryOrNull { smsToExpenseDebitCard(words) }
            is CardType.CreditCard -> tryOrNull { smsToExpenseCreditCard(words) }
            is CardType.Unknown -> null
        }
    }

    override fun checkCardType(words: List<String>): CardType {
        return super.checkCardType(words)
    }

    override fun smsToExpenseDebitCard(words: List<String>): Expense {
        return Expense()
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        val n = words[0].split(" ")

        val approved = n[0].endsWith("승인")

        val user = n[1]

        val card = n[0]
                .substring(0, n[0].length - 2)
                .replace(onlyLetterRegex, "")

        val c = words[1].split(" ")

        val charge = c[0]
                .substring(0, c[0].length - 1)
                .replace(",", "")
                .toInt()

        val installment = c[1] == "일시불"

        val d = words[2].split(" ")

        val date = d[0].split("/")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val time = d[1].split(":")

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val shop = d[2]

        /* 비고 */
        val remark = words[3]

        return Expense(
                card = Card(bank = "현대카드", cardName = card),
                approved = approved,
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0))
    }
}