package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
[Web발신]
신한체크승인 장*운(3*9*) 01/31 12:19 10,000원 주식회사비케이 잔액5,163원

[Web발신]
[신한체크승인] 이*원(5780) 08/10 20:53 (금액)53,000원 후암연어식당
*/
object Shinhan: Parser() {

    override fun smsToExpense(origin: String): Expense? {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split(" ")

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

    override fun smsToExpenseDebitCard(words: List<String>): Expense {
        val approved = words[0]
                .replace(onlyLetterRegex, "") == "신한체크승인"

        val a = words[1].split("(")

        val user = a[0]

        val cardNumber = a[1].substring(0, a[1].length - 1)

        val date = words[2].split("/")

        val time = words[3].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val charge = words[4]
                .takeIf { it.startsWith("(금액)") }
                ?.let { charge ->
                    charge.substring(4, words[4].length - 1)
                            .replace(",", "")
                            .toInt()
                }
                ?:words[4].substring(0, words[4].length - 1)
                        .replace(",", "")
                        .toInt()


        val shop = words[5]

        val accumulation
                = words.takeIf { it.size > 6 }
                ?.get(6)
                ?.let { accumulation ->
                    accumulation
                            .replace("잔액", "")
                            .replace(onlyLetterRegex, "")
                            .let {
                                it.substring(0, it.length - 1)
                                        .replace(",", "")
                            }
                }

        return Expense(
                card = Card(bank = "신한카드", cardNumber = cardNumber),
                dateTime = Date(2018, month, day, hour, minute, 0),
                charge = charge,
                approved = approved)
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        return Expense()
    }
}