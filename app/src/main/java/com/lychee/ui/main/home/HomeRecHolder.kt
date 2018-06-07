package com.lychee.ui.main.home

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.extensions.inflate
import com.lychee.extensions.won
import kotlinx.android.synthetic.main.rec_item_home.view.*

class HomeRecHolder constructor(
        parent : ViewGroup
) : RecyclerView.ViewHolder(parent.inflate(R.layout.rec_item_home)) {

    private val shopName = itemView.shop_name
    private val card = itemView.card
    private val date = itemView.date
    private val price = itemView.price

    // TODO more click event 밖에서 선언

    fun bind(data : Expenditure) {
        data.let {
            shopName.text = it.shopName
            card.text = it.cardName
            date.text = it.date
            price.text = it.price.won(true) // won with comma
        }
    }
}