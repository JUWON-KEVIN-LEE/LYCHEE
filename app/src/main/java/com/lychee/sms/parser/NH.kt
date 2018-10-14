package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
체크
[Web발신]
NH카드9*3*승인
윤*민
56,000원 체크
08/15 15:27
낭트

신용

*/
object NH: Parser() {

    override fun smsToExpense(origin: String): Expense? {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split("\n")

        val type = checkCardType(words)

        return when(type) {
            is CardType.DebitCard -> tryOrNull { IBK.smsToExpenseDebitCard(words) }
            is CardType.CreditCard -> tryOrNull { IBK.smsToExpenseCreditCard(words) }
            is CardType.Unknown -> null
        }
    }

    override fun checkCardType(words: List<String>): CardType {
        return super.checkCardType(words)
    }

    override fun smsToExpenseDebitCard(words: List<String>): Expense {
        val approved = words[0].contains("승인")

        val user = words[1]

        val cardNumber = words[0].substring(4, 8)

        val charge = words[2]
                .split(" ")[0]
                .let {
                    it.substring(0, it.length - 1)
                        .replace(",", "")
                        .toInt()
                }

        val d = words[3].split(" ")

        val date = d[0].split("/")

        val time = d[1].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val shop = words[4]

        return Expense(
                approved = approved,
                card = Card(bank = "NH농협카드", cardNumber = cardNumber),
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0))
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        return Expense()
    }
}