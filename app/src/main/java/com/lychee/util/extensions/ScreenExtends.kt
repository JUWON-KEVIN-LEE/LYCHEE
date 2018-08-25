package com.lychee.util.extensions

import android.content.Context

fun Context.dpToPx(dp : Float) : Float = dp * resources.displayMetrics.density

fun Context.dpToPx(dp : Int) : Int = Math.round(dp * resources.displayMetrics.density)