package com.lychee.ui.cardlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.data.model.Card
import com.lychee.extensions.inflate
import com.lychee.extensions.invisible
import com.lychee.extensions.visible
import kotlinx.android.synthetic.main.view_card.view.*

class CardListAdapter constructor(
        private val context : Context,
        private val data : List<Card>
) : PagerAdapter() {

    private val cache : MutableMap<Int, View> = mutableMapOf()

    var editMode : Boolean = false
    set(value) { field = value; onEditMode(value) }

    override fun getCount(): Int = data.size + DEFAULT_PAGE_SIZE

    override fun instantiateItem(pager: ViewGroup, position: Int): Any {
        when(position) {
            DEFAULT_PAGE -> {
                val page = pager.inflate(R.layout.view_add_card)

                // TODO ON CLICK > ADD PAGE

                pager.addView(page)

                return page
            }
            else -> {
                val page = pager.inflate(R.layout.view_card)

                // background
                if(position % 2 == 0) { page.image_card.background = ContextCompat.getDrawable(context, R.drawable.card_drawable_purple) }
                // bank
                page.bank.text = data[position-1].bank
                // card number
                data[position-1].cardNumber.apply {
                    val cardNumber = get(0) + " " + get(1) + " " + get(2) + " " + get(3)
                    page.card_number.append(cardNumber)
                }
                // edit
                page.edit_mode.takeIf { editMode }?.visible()

                pager.addView(page)

                onCache(position, page)

                return page
            } // page.image_card.loadImage(data[position].url)
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { } // = container.removeView(`object` as View)

    private fun onCache(position : Int, page : View) { cache[position] = page }

    private fun onEditMode(mode : Boolean) {
        if(mode) cache.map { it.value.edit_mode.visible() }
        else cache.map { it.value.edit_mode.invisible() }
    }

    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 1
    }
}