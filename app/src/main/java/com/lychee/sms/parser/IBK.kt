package com.lychee.sms.parser

import com.lychee.data.core.model.Card
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.tryOrNull

/*
[Web발신]
08/13 12:33
출금 **원
잔액 ***원
씨유시흥본점
666*771080
기업
 */
object IBK: Parser() {

    private val mCard = Card(bank = "기업")

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
     * 카드사 메시지가 아니라 은행 메시지인 것 같음.
     */
    override fun smsToExpenseDebitCard(words: List<String>): Expense {
        val cardNumber = words[4]

        val charge = words[1]
                .split(" ")[1]
                .let {
                    it.substring(0, it.length - 1)
                            .replace(",", "")
                            .toInt()
                }

        val d = words[0].split(" ")

        val date = d[0]

        val time = d[1]

        val shop = words[3]

        val balance = words[2]
                .split(" ")[1]
                .let {
                    it.substring(0, it.length - 1)
                        .replace(",", "")
                        .toInt()
                }

        return Expense()
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        return Expense()
    }
}