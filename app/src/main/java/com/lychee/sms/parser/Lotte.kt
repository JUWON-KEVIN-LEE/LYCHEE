package com.lychee.sms.parser

import com.lychee.data.core.model.Expense

object Lotte: Parser() {

    override fun smsToExpense(origin: String): Expense? {
        return null
    }

    override fun checkCardType(words: List<String>): CardType {
        return CardType.Unknown()
    }

    override fun smsToExpenseDebitCard(words: List<String>): Expense {
        return Expense()
    }

    override fun smsToExpenseCreditCard(words: List<String>): Expense {
        return Expense()
    }
}