package com.lychee.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * View Extensions
 */
fun ViewGroup.inflate(resId : Int) : View
    = inflate(resId, false)

fun ViewGroup.inflate(resId : Int, attachToRoot : Boolean) : View
        = LayoutInflater.from(context).inflate(resId, this, attachToRoot)