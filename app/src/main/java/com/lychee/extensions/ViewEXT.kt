package com.lychee.extensions

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewCompat
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

// UNUSED
fun View.elevation(dp : Float) { ViewCompat.setElevation(this, dp) }
fun View.translationZ(value : Float) { ViewCompat.setTranslationZ(this, value) }

// VIEW GROUP
fun ViewGroup.replaceFragment(supportFragmentManager: FragmentManager, fragment: Fragment)
    = supportFragmentManager.beginTransaction().replace(this.id, fragment).commit()

fun ViewGroup.removeFragment(supportFragmentManager: FragmentManager, fragment: Fragment)
    = supportFragmentManager.beginTransaction().remove(fragment).commit()

fun ViewGroup.inflate(resId : Int) : View
    = inflate(resId, false)

fun ViewGroup.inflate(resId : Int, attachToRoot : Boolean) : View
        = LayoutInflater.from(context).inflate(resId, this, attachToRoot)

// CONSTRAINT LAYOUT
fun ConstraintLayout.update(constraintSet: ConstraintSet = ConstraintSet(), update : ConstraintSet.() -> Unit) {
    constraintSet.clone(this)
    constraintSet.update()
    constraintSet.applyTo(this)
}

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