package com.lychee.ui.main.record

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.extensions.inflate
import kotlinx.android.synthetic.main.rec_item_record.view.*

class RecordRecHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.rec_item_record)
) {

    private val time = itemView.time
    private val category = itemView.category
    private val shopName = itemView.shop_name
    private val card = itemView.card
    private val date = itemView.date
    private val price = itemView.price
    private val more = itemView.more

    fun bind(data : Expenditure) {
        data.let {
            time.text = it.time
            // category
            shopName.text = it.shopName
            card.text = it.cardId
            date.text = it.date
            price.text = it.price
        }
    }
}