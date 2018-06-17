package com.lychee.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.lychee.R
import kotlinx.android.synthetic.main.view_month_selector.view.*
import java.util.*

/**
 * TODO
 * Month Change Listener
 */
class MonthSelector : FrameLayout {

    // VIEW
    private val previousButton : View
    private val nextButton : View

    private val monthView : TextView
    private val yearView : TextView

    // VARIABLES
    // TODO bind /w view model
    var mMonth : Int = 0
    set(value) {
        if((value >= 1).and(value <= 12)) field = value
        setMonth()
    }

    var mYear : Int = 0
    set(value) {
        if(value > 0) field = value
        setYear()
    }

    // CONSTRUCTOR
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // attach & bind
        LayoutInflater.from(context).inflate(R.layout.view_month_selector, this)

        previousButton = button_previous
        nextButton = button_next

        monthView = view_month
        yearView = view_year

        init()
    }

    private fun init() {
        previousButton.setOnClickListener{ previous() }
        nextButton.setOnClickListener{ next() }

        // init date
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val calendar = Calendar.getInstance(timeZone)

        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH) + 1 // start 0
    }

    private fun previous() {
        if(mMonth == 1) { mMonth = 12; mYear -= 1; setMonth(); setYear() }
        else { mMonth -= 1; setMonth() }
    }

    private fun next() {
        if(mMonth == 12) { mMonth = 1; mYear += 1; setMonth(); setYear() }
        else { mMonth += 1; setMonth() }
    }

    private fun setMonth() { monthView.text = ("${mMonth}월") }

    private fun setYear() { yearView.text = ("${mYear}년") }
}