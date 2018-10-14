package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull
import java.util.*

/*
신용
[Web발신]
하나(1*2*) 승인 김*원님 25,000원 일시불 08/13 20:50 진원조보신 누적 4,055,876원
*/
object Hana: Parser() {

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
        return Expense()
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        val approved = words[1].contains("승인")

        val user = words[2].substring(0, words[2].length - 1)

        val cardNumber = words[0]
                .split("(")[1]
                .let { it.substring(0, it.length - 1) }

        val date = words[5].split("/")

        val time = words[6].split(":")

        val month = date[0].toInt()
        val day = date[1].toInt()

        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val charge = words[3]
                .substring(0, words[3].length - 1)
                .replace(",", "")
                .toInt()

        val shop = words[7]

        val installment = words[4] == "일시불"

        val accumulation = words[9]
                .substring(0, words[9].length - 1)
                .replace(",", "")
                .toInt()

        return Expense(
                card = Card(bank = "하나카드", cardNumber = cardNumber),
                charge = charge,
                dateTime = Date(2018, month, day, hour, minute, 0),
                approved = approved)
    }

    /*
    신용카드
    fun smsToExpense(origin: String) {
        val body = origin.removePrefix("[Web발신]\n")

        val words = body.split(" ")

        val hana = words[0].startsWith("하나") /* 필요? */

        val approved = words[1] == "승인" /* 거절 */

        if(hana && approved) {

        }
    }
    */
}