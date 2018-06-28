package com.lychee.extensions

import android.content.Context
import android.support.annotation.IntRange
import android.widget.Toast

fun Context.toast(message : String, @IntRange(from = 0, to = 1) length : Int)
        = Toast.makeText(this, message, length).show()

fun Context.dpToPx(dp : Float) : Float = dp * this.resources.displayMetrics.density

fun Context.dpToPx(dp : Int) : Int = Math.round(dp * this.resources.displayMetrics.density)

fun Context.pxToDp(px : Float) : Float = px / this.resources.displayMetrics.density
