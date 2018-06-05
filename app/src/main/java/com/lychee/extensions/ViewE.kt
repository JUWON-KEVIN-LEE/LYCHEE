package com.lychee.extensions

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.reflect.Field



/**
 * View Extensions
 */
// VIEW GROUP
fun ViewGroup.inflate(resId : Int) : View
    = inflate(resId, false)

fun ViewGroup.inflate(resId : Int, attachToRoot : Boolean) : View
        = LayoutInflater.from(context).inflate(resId, this, attachToRoot)


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