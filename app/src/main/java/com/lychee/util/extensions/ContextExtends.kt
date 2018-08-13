package com.lychee.util.extensions

import android.content.Context
import android.support.annotation.IntRange
import android.widget.Toast

// TODO Extends 빈번하게 사용되지 않을 함수들 정리하기.
// DISPLAY
fun Context.deviceWidth() = resources.displayMetrics.widthPixels

fun Context.deviceHeight() = resources.displayMetrics.heightPixels

fun Context.dpToPx(dp : Float) : Float = dp * resources.displayMetrics.density

fun Context.dpToPx(dp : Int) : Int = Math.round(dp * resources.displayMetrics.density)

fun Context.pxToDp(px : Float) : Float = px / this.resources.displayMetrics.density

// TOAST
fun Context.toast(message : String, @IntRange(from = 0, to = 1) length : Int)
        = Toast.makeText(this, message, length).show()

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}