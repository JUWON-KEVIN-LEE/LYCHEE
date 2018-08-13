package com.lychee.ui.main.page.home.adapter

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

@BindingAdapter("setPrice")
fun TextView.setPrice(price: String) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)
    val finalPrice = currencyFormat.format(price.toInt()) + "원"
    text = finalPrice
}