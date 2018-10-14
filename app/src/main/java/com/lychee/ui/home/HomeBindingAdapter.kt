package com.lychee.ui.home

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.lychee.R
import java.text.NumberFormat
import java.util.*

@BindingAdapter("toWon")
fun TextView.toWon(price: Int) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)

    text= if(price < 0) "-${currencyFormat.format((-1 * price)).removePrefix(currencyFormat.currency.symbol)}"
    else currencyFormat.format(price).removePrefix(currencyFormat.currency.symbol)
}

fun TextView.setDate(date: String) {
    val d = date.split("/")

    val month = d[0]
    val date = d[1]

    text = "${month}월 ${date}일"
}

@BindingAdapter("count")
fun TextView.count(count: Int) {
    text = "총 ${count}건"
}

@BindingAdapter("balance")
fun TextView.balance(balance: Int) {
    when {
        balance > 0 -> setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        balance == 0 -> setTextColor(ContextCompat.getColor(context, android.R.color.white))
        else -> setTextColor(ContextCompat.getColor(context, R.color.Red))
    }
}