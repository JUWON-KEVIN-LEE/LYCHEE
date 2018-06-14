package com.lychee.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.extensions.inflate
import com.lychee.extensions.visible
import com.lychee.view.calendar.model.LycheeDay
import kotlinx.android.synthetic.main.view_calendar_day.view.*

class LycheeWeekView : LinearLayout {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private fun init() {

    }

    fun addDay(lycheeDay : LycheeDay) {
        val view = inflate(R.layout.view_calendar_day)

        view.apply {
            day.text = lycheeDay.day.toString()
            lycheeDay.expenditure
                    ?.let {
                        for(index in 0 until it.size) invalidateExpenditure(index, it[index])

                        if(it.size > 3) {
                            more.apply {
                                visible()
                                text = "+${it.size - 3}"
                            }
                        }
                    }
        }

        addView(view, LinearLayout.LayoutParams(0, MATCH_PARENT, 1f).apply { leftMargin = 4; rightMargin = 4 })
    }

    private fun invalidateExpenditure(index : Int, expenditure : Expenditure) {
        when(index) {
            0 -> {
                expenditure_0.apply {
                    visible()
                    text = expenditure.shopName
                    // TODO CATEGORY /W COLOR
                }
            }
            1 -> {
                expenditure_1.apply {
                    visible()
                    text = expenditure.shopName
                    // TODO CATEGORY /W COLOR
                }
            }
            2 -> {
                expenditure_2.apply {
                    visible()
                    text = expenditure.shopName
                    // TODO CATEGORY /W COLOR
                }
            }
            else -> {}
        }
    }
}