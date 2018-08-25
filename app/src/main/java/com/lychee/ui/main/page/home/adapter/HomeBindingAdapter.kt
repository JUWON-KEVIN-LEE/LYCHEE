package com.lychee.ui.main.page.home.adapter

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.lychee.R
import java.text.NumberFormat
import java.util.*

@BindingAdapter("toWon")
fun TextView.toWon(price: Int) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)

    text= if(price < 0) "-${currencyFormat.format((-1 * price)).removePrefix(currencyFormat.currency.symbol)}원"
    else "${currencyFormat.format(price).removePrefix(currencyFormat.currency.symbol)}원"
}

@BindingAdapter("toCount")
fun TextView.toCount(count: Int) {
    text = "${count}건"
}

@BindingAdapter("highlightBalance")
fun TextView.highlightBalance(balance: Int) {
    if(balance >= 0) setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    else setTextColor(ContextCompat.getColor(context, R.color.Red))
}