package com.lychee.sms.parser

import com.lychee.data.core.model.Expense

abstract class Parser {

    val onlyLetterRegex = Regex("^[가-힣a-zA-Z]*$")

    abstract fun smsToExpense(origin: String): Expense?

    open fun checkCardType(words: List<String>): CardType {
        words.forEach { word ->
            val debit = word.contains("체크")

            if(debit) return CardType.DebitCard()

            val credit = word.contains("일시불")
                    || word.contains("할부")
                    || word.contains("개월")

            if(credit) return CardType.CreditCard()
        }

        return CardType.Unknown()
    }

    abstract fun smsToExpenseDebitCard(words: List<String>): Expense

    abstract fun smsToExpenseCreditCard(words: List<String>): Expense

    sealed class CardType {
        class DebitCard: CardType()
        class CreditCard: CardType()
        class Unknown: CardType()
    }
}