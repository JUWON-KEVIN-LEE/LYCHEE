package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
신용
[Web발신]
삼성6125승인
202,000원 일시불
10/19 12:28 ㈜일현전자
*/
object Samsung: Parser() {

    override fun smsToExpense(origin: String): Expense? {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split("\n")

        val type = NH.checkCardType(words)

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
        return Expense()
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        val approved = words[0].contains("승인")

        val cardNumber = words[0].substring(2, 6) // UNSAFE

        val c = words[1].split(" ")

        val charge = c[0]
                .substring(0, c[0].length - 1)
                .replace(",", "")
                .toInt()

        val installment = c[1] == "일시불" /* n개월 */

        val d = words[2].split(" ")

        val date = d[0].split("/")

        val time = d[1].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val shop = d[2]

        return Expense(
                approved = approved,
                card = Card(bank = "삼성카드", cardNumber = cardNumber),
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0))
    }
}