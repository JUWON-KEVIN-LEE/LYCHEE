package com.lychee.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.lychee.view.calendar.model.MDate

abstract class BaseView : View, View.OnClickListener {

    lateinit var mDates : List<MDate>

    abstract var mCurrentDate : Int

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
}