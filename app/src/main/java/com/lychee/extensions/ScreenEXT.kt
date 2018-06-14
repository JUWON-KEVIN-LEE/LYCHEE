package com.lychee.extensions

import android.app.Activity
import android.util.DisplayMetrics

fun Activity.deviceWidth() : Int
        = DisplayMetrics().let { windowManager.defaultDisplay.getMetrics(it); it.widthPixels }

fun Activity.deviceHeight() : Int
        = DisplayMetrics().let { windowManager.defaultDisplay.getMetrics(it); it.heightPixels }

