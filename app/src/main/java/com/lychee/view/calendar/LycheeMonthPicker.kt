package com.lychee.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lychee.R
import kotlinx.android.synthetic.main.view_month_picker.view.*
import java.util.*

/**
 * TODO
 * Month Change Listener
 */
class LycheeMonthPicker : LinearLayout {

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
        setMonthText()
    }

    var mYear : Int = 0
    set(value) {
        if(value > 0) field = value
        setYearText()
    }

    // CONSTRUCTOR
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        // attach & bind
        LayoutInflater.from(context).inflate(R.layout.view_month_picker, this)

        previousButton = button_previous
        previousButton.setOnClickListener{ previous() }

        nextButton = button_next
        nextButton.setOnClickListener{ next() }

        monthView = view_month
        yearView = view_year

        // init date
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val calendar = Calendar.getInstance(timeZone)

        mYear = calendar.get(Calendar.YEAR)
                 // start 0
        mMonth = calendar.get(Calendar.MONTH) + 1

        setMonthText()
        setYearText()
    }

    private fun previous() {
        if(mMonth == 1) { mMonth = 12; mYear -= 1; setMonthText(); setYearText() }
        else { mMonth -= 1; setMonthText() }
    }

    private fun next() {
        if(mMonth == 12) { mMonth = 1; mYear += 1; setMonthText(); setYearText() }
        else { mMonth += 1; setMonthText() }
    }

    private fun setMonthText() { monthView.text = ("${mMonth}월") }

    private fun setYearText() { yearView.text = ("${mYear}년") }
}