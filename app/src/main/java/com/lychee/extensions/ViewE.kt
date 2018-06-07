package com.lychee.extensions

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.lang.reflect.Field



/**
 * View Extensions
 */
// VIEW
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }

// VIEW GROUP
fun ViewGroup.inflate(resId : Int) : View
    = inflate(resId, false)

fun ViewGroup.inflate(resId : Int, attachToRoot : Boolean) : View
        = LayoutInflater.from(context).inflate(resId, this, attachToRoot)

// GLIDE
fun ImageView.loadImage(url : String)
        = Glide.with(this).load(url).into(this)

fun ImageView.loadImage(url : String, options: RequestOptions)
        = Glide.with(this).load(url).apply(options).into(this)

// BOTTOM NAVIGATION VIEW
@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menu: BottomNavigationMenuView = this.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode : Field = menu.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.apply {
            isAccessible = true
            setBoolean(menu, false)
            isAccessible = false
        }
        for (i in 0 until menu.childCount) {
            val item = menu.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            item.setChecked(item.itemData.isChecked)
        }
    }
    catch (e : NoSuchFieldException) { Log.e("JUWONLEE", "Unable to get shift mode field") }
    catch (e : IllegalAccessException) { Log.e("JUWONLEE", "Unable to change value of shift mode") }
}