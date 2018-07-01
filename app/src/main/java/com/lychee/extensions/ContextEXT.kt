package com.lychee.extensions

import android.content.Context
import android.support.annotation.IntRange
import android.widget.Toast

// DISPLAY
fun Context.deviceWidth() = resources.displayMetrics.widthPixels

fun Context.deviceHeight() = resources.displayMetrics.heightPixels

fun Context.dpToPx(dp : Float) : Float = dp * this.resources.displayMetrics.density

fun Context.dpToPx(dp : Int) : Int = Math.round(dp * this.resources.displayMetrics.density)

fun Context.pxToDp(px : Float) : Float = px / this.resources.displayMetrics.density

// TOAST
fun Context.toast(message : String, @IntRange(from = 0, to = 1) length : Int)
        = Toast.makeText(this, message, length).show()