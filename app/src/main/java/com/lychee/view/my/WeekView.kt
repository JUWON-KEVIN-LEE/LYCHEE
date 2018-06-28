package com.lychee.view.my

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.lychee.R
import com.lychee.extensions.inflate

class WeekView : LinearLayout {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private fun init() {

    }

    fun addDay() {
        val view = inflate(R.layout.view_calendar_day)

        view.apply {

        }

        addView(view, LinearLayout.LayoutParams(0, MATCH_PARENT, 1f).apply { leftMargin = 4; rightMargin = 4 })
    }
}